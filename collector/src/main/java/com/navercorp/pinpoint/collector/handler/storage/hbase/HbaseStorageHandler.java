package com.navercorp.pinpoint.collector.handler.storage.hbase;

import com.navercorp.pinpoint.collector.handler.storage.BaseStorageHandler;
import com.navercorp.pinpoint.collector.service.AgentEventService;
import com.navercorp.pinpoint.collector.service.AgentInfoService;
import com.navercorp.pinpoint.collector.service.ApiMetaDataService;
import com.navercorp.pinpoint.collector.service.HBaseAgentStatService;
import com.navercorp.pinpoint.collector.service.SqlMetaDataService;
import com.navercorp.pinpoint.collector.service.StringMetaDataService;
import com.navercorp.pinpoint.collector.service.TraceService;
import com.navercorp.pinpoint.common.server.bo.AgentInfoBo;
import com.navercorp.pinpoint.common.server.bo.ApiMetaDataBo;
import com.navercorp.pinpoint.common.server.bo.SpanBo;
import com.navercorp.pinpoint.common.server.bo.SpanChunkBo;
import com.navercorp.pinpoint.common.server.bo.SqlMetaDataBo;
import com.navercorp.pinpoint.common.server.bo.StringMetaDataBo;
import com.navercorp.pinpoint.common.server.bo.event.AgentEventBo;
import com.navercorp.pinpoint.common.server.bo.stat.AgentStatBo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async("hbaseStorageExecutor")
public class HbaseStorageHandler implements BaseStorageHandler {

    @Autowired
    private AgentEventService agentEventService;

    @Autowired
    private AgentInfoService agentInfoService;

    @Autowired
    private HBaseAgentStatService hBaseAgentStatService;

    @Autowired
    private ApiMetaDataService apiMetaDataService;

    @Autowired
    private TraceService traceService;

    @Autowired
    private SqlMetaDataService sqlMetaDataService;

    @Autowired
    private StringMetaDataService stringMetaDataService;

    @Override
    public void handle(AgentEventBo agentEventBo) {
        agentEventService.insert(agentEventBo);
    }

    @Override
    public void handle(List<AgentEventBo> agentEventBoList) {
        for (AgentEventBo agentEventBo : agentEventBoList) {
            agentEventService.insert(agentEventBo);
        }
    }

    @Override
    public void handle(AgentInfoBo agentInfoBo) {
        agentInfoService.insert(agentInfoBo);
    }

    @Override
    public void handle(AgentStatBo agentStatBo) {
        hBaseAgentStatService.save(agentStatBo);
    }

    @Override
    public void handle(ApiMetaDataBo apiMetaDataBo) {
        apiMetaDataService.insert(apiMetaDataBo);
    }

    @Override
    public void handle(SpanBo spanBo) {
        traceService.insertSpan(spanBo);
    }

    @Override
    public void handle(SpanChunkBo spanChunkBo) {
        traceService.insertSpanChunk(spanChunkBo);
    }

    @Override
    public void handle(SqlMetaDataBo sqlMetaDataBo) {
        sqlMetaDataService.insert(sqlMetaDataBo);
    }

    @Override
    public void handle(StringMetaDataBo stringMetaDataBo) {
        stringMetaDataService.insert(stringMetaDataBo);
    }
}
