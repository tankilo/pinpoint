package com.navercorp.pinpoint.plugin.itracing.interceptor;

import com.navercorp.pinpoint.bootstrap.context.MethodDescriptor;
import com.navercorp.pinpoint.bootstrap.context.SpanRecorder;
import com.navercorp.pinpoint.bootstrap.context.Trace;
import com.navercorp.pinpoint.bootstrap.context.TraceContext;
import com.navercorp.pinpoint.bootstrap.interceptor.SpanSimpleAroundInterceptor;
import com.navercorp.pinpoint.plugin.itracing.ItracingConstants;
import com.tankilo.pinpoint.collector.receiver.udp.UDPReceiver;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;

public class UDPReceiverInterceptor extends SpanSimpleAroundInterceptor {
    protected UDPReceiverInterceptor(TraceContext traceContext,
        MethodDescriptor methodDescriptor,
        Class<? extends SpanSimpleAroundInterceptor> childClazz) {
        super(traceContext, methodDescriptor, childClazz);
    }

    protected UDPReceiverInterceptor(TraceContext traceContext,
        MethodDescriptor methodDescriptor) {
        super(traceContext, methodDescriptor, UDPReceiverInterceptor.class);
    }

    @Override protected void doInBeforeTrace(SpanRecorder recorder, Object o, Object[] objects) {
        final UDPReceiver receiver = (UDPReceiver) o;
        recorder.recordServiceType(ItracingConstants.ITRACING_COLLECTOR_SERVICE_TYPE);
        recorder.recordApi(methodDescriptor);

        Field privateField = null;
        try {
            privateField = UDPReceiver.class.getDeclaredField("bindAddress");
            privateField.setAccessible(true);
            InetSocketAddress inetSocketAddress = (InetSocketAddress) privateField.get(receiver);
            recorder.recordAttribute(ItracingConstants.ITRACING_LOCAL_ADDRESS_ANNOTATION_KEY,
                inetSocketAddress.toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override protected Trace createTrace(Object o, Object[] objects) {
        return this.traceContext.newTraceObject();
    }

    @Override
    protected void doInAfterTrace(SpanRecorder recorder, Object o, Object[] objects, Object o1, Throwable throwable) {

    }
}
