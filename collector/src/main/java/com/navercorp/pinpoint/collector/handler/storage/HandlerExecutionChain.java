package com.navercorp.pinpoint.collector.handler.storage;

import com.navercorp.pinpoint.common.server.bo.AgentInfoBo;
import com.navercorp.pinpoint.common.server.bo.ApiMetaDataBo;
import com.navercorp.pinpoint.common.server.bo.SpanBo;
import com.navercorp.pinpoint.common.server.bo.SpanChunkBo;
import com.navercorp.pinpoint.common.server.bo.SqlMetaDataBo;
import com.navercorp.pinpoint.common.server.bo.StringMetaDataBo;
import com.navercorp.pinpoint.common.server.bo.event.AgentEventBo;
import com.navercorp.pinpoint.common.server.bo.stat.AgentStatBo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class HandlerExecutionChain implements BaseStorageHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<BaseStorageHandler> baseStorageHandlers;

    @Autowired
    public HandlerExecutionChain(List<BaseStorageHandler> baseStorageHandlers, ListableBeanFactory beanFactory) {
        this.baseStorageHandlers = baseStorageHandlers;
    }

    @Override
    public void handle(AgentEventBo agentEventBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(agentEventBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(List<AgentEventBo> agentEventBoList) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(agentEventBoList);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(AgentInfoBo agentInfoBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(agentInfoBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(AgentStatBo agentStatBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(agentStatBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(ApiMetaDataBo apiMetaDataBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(apiMetaDataBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override public void handle(SpanBo spanBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(spanBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(SpanChunkBo spanChunkBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(spanChunkBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(SqlMetaDataBo sqlMetaDataBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(sqlMetaDataBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }

    @Override
    public void handle(StringMetaDataBo stringMetaDataBo) {
        if (!ObjectUtils.isEmpty(baseStorageHandlers)) {
            for (BaseStorageHandler handler : baseStorageHandlers) {
                try {
                    handler.handle(stringMetaDataBo);
                } catch (Throwable t) {
                    logger.error("BaseStorageHandler handle threw exception", t);
                }
            }
        }
    }
}
