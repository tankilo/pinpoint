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

public interface BaseStorageHandler {
    void handle(final AgentEventBo agentEventBo);

    void handle(final List<AgentEventBo> agentEventBoList);

    void handle(final AgentInfoBo agentInfoBo);

    void handle(final AgentStatBo agentStatBo);

    void handle(final ApiMetaDataBo apiMetaDataBo);

    void handle(final SpanBo spanBo);

    void handle(final SpanChunkBo spanChunkBo);

    void handle(final SqlMetaDataBo sqlMetaDataBo);

    void handle(final StringMetaDataBo stringMetaDataBo);
}
