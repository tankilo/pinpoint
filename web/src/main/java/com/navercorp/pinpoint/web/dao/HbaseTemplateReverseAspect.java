package com.navercorp.pinpoint.web.dao;

import com.navercorp.pinpoint.common.hbase.HbaseOperations2;
import com.navercorp.pinpoint.common.hbase.HbaseTable;
import com.navercorp.pinpoint.common.hbase.HbaseTableNameProvider;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Tankilo
 */
@Aspect
public class HbaseTemplateReverseAspect {

    @Autowired
    @Qualifier("hbaseTemplateReverse")
    private HbaseOperations2 template2;

    @Autowired
    private HbaseTableNameProvider tableNameProvider;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.navercorp.pinpoint.common.hbase.RowMapper.mapRow(..))")
    public void pointCut() {
    }

    @After("pointCut() && args(result,rowNum)")
    public void doBefore(Result result, int rowNum) {
        if (ThreadContext.isReverse()) {
            String tableNameStr = ThreadContext.getTableName();
            TableName tableName = tableNameProvider.getTableName(tableNameStr);
            Put put = new Put(result.getRow());
            Cell[] rawCells = result.rawCells();
            for (Cell cell : rawCells) {
                put.addColumn(CellUtil.cloneFamily(cell), CellUtil.cloneQualifier(cell), cell.getTimestamp(), CellUtil.cloneValue(cell));
            }
            template2.asyncPut(tableName, put);
        }
    }

    @Before("execution(public * com.navercorp.pinpoint.web.dao.hbase.HbaseTraceDaoV2.selectSpan(..))")
    public void selectSpan() {
        ThreadContext.setTableName(HbaseTable.TRACE_V2.getName());
    }

    @AfterReturning(returning = "tableName", pointcut = "execution(public * com.navercorp.pinpoint.common.hbase.TableDescriptor.getTableName())")
    public void getTableName(TableName tableName) {
        ThreadContext.setTableName(tableName.getNameAsString());
    }

    @Around("execution(public * com.navercorp.pinpoint.web.service.SpanService.selectSpan(..))")
    public Object dealContext(ProceedingJoinPoint jp) throws Throwable {
        Object result;
        try {
            ThreadContext.setReverse(true);
            result = jp.proceed();
        } finally {
            ThreadContext.remove();
        }
        return result;
    }
}
