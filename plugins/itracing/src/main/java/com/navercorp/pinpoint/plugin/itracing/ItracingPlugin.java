package com.navercorp.pinpoint.plugin.itracing;

import com.tankilo.pinpoint.bootstrap.instrument.transformer.TransformTemplate;
import com.tankilo.pinpoint.bootstrap.instrument.transformer.TransformTemplateAware;
import com.tankilo.pinpoint.bootstrap.plugin.ProfilerPlugin;
import com.tankilo.pinpoint.bootstrap.plugin.ProfilerPluginSetupContext;

public class ItracingPlugin implements ProfilerPlugin, TransformTemplateAware {

    private TransformTemplate transformTemplate;

    @Override
    public void setTransformTemplate(TransformTemplate transformTemplate) {

    }

    @Override
    public void setup(ProfilerPluginSetupContext context) {

    }
}
