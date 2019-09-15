package com.navercorp.pinpoint.plugin.itracing;

import com.navercorp.pinpoint.common.trace.TraceMetadataProvider;
import com.navercorp.pinpoint.common.trace.TraceMetadataSetupContext;

public class ItracingTraceMetadataProvider implements TraceMetadataProvider {
    @Override
    public void setup(TraceMetadataSetupContext context) {
        context.addServiceType(ItracingConstants.ITRACING_COLLECTOR_SERVICE_TYPE);
        context.addServiceType(ItracingConstants.ITRACING_WEB_SERVICE_TYPE);
        context.addAnnotationKey(ItracingConstants.ITRACING_LOCAL_ADDRESS_ANNOTATION_KEY);
        context.addAnnotationKey(ItracingConstants.ITRACING_REMOTE_ADDRESS_ANNOTATION_KEY);
    }
}