package com.navercorp.pinpoint.plugin.itracing;

import com.navercorp.pinpoint.bootstrap.instrument.InstrumentClass;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentException;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentMethod;
import com.navercorp.pinpoint.bootstrap.instrument.Instrumentor;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformCallback;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplate;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplateAware;
import com.navercorp.pinpoint.bootstrap.interceptor.scope.ExecutionPolicy;
import com.navercorp.pinpoint.bootstrap.interceptor.scope.InterceptorScope;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPlugin;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPluginSetupContext;
import java.security.ProtectionDomain;

public class ItracingPlugin implements ProfilerPlugin, TransformTemplateAware {
    private static final String SCOPE_NAME1 = "UDPReceiver";

    private TransformTemplate transformTemplate;

    @Override
    public void setTransformTemplate(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }

    @Override
    public void setup(ProfilerPluginSetupContext context) {
        this.addTransformers();

    }

    private void addTransformers() {
        transformTemplate.transform("com.tankilo.pinpoint.collector.receiver.udp.UDPReceiver", new TransformCallback() {
            @Override
            public byte[] doInTransform(Instrumentor instrumentor, ClassLoader loader, String className,
                Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                byte[] classfileBuffer) throws InstrumentException {
                final InstrumentClass target = instrumentor.getInstrumentClass(loader, className, classfileBuffer);
                InstrumentMethod invokeMethod = target.getDeclaredMethod("receive", "java.net.DatagramSocket");
                if (invokeMethod != null) {
                    invokeMethod.addInterceptor("com.navercorp.pinpoint.plugin.itracing.interceptor.UDPReceiverInterceptor");
                }
                return target.toBytecode();
            }
        });

        transformTemplate.transform("com.tankilo.pinpoint.collector.receiver.udp.Task", new TaskTransformer());
    }

    class TaskTransformer implements TransformCallback {

        @Override public byte[] doInTransform(Instrumentor instrumentor, ClassLoader classLoader, String className,
            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws InstrumentException {
            InterceptorScope scope = instrumentor.getInterceptorScope(SCOPE_NAME1);

            InstrumentClass target = instrumentor.getInstrumentClass(classLoader, className, classfileBuffer);
            target.addField("com.navercorp.pinpoint.bootstrap.async.AsyncContextAccessor");
            InstrumentMethod constructor = target.getConstructor("java.lang.String", "com.navercorp.plugin.sample.target.TargetClass12_Future");
            constructor.addScopedInterceptor("WorkerConstructorInterceptor", scope, ExecutionPolicy.INTERNAL);
            InstrumentMethod run = target.getDeclaredMethod("run");
            run.addInterceptor("com.navercorp.pinpoint.plugin.itracing.interceptor.TaskRunInterceptor");
            return target.toBytecode();
        }
    }

}
