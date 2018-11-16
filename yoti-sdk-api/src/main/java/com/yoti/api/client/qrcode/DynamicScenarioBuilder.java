package com.yoti.api.client.qrcode;

import java.util.ServiceLoader;

import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

public abstract class DynamicScenarioBuilder {

    public static final DynamicScenarioBuilder newInstance() {
        ServiceLoader<DynamicScenarioBuilder> dynamicScenarioBuilderLoader = ServiceLoader.load(DynamicScenarioBuilder.class);
        if (!dynamicScenarioBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of DynamicScenarioBuilder");
        }
        DynamicScenarioBuilder dynamicScenarioBuilder = dynamicScenarioBuilderLoader.iterator().next();
        return dynamicScenarioBuilder.createDynamicScenarioBuilder();
    }

    protected abstract DynamicScenarioBuilder createDynamicScenarioBuilder();

    public abstract DynamicScenarioBuilder withCallbackEndpoint(String callbackEndpoint);

    public abstract DynamicScenarioBuilder withPolicy(DynamicPolicy dynamicPolicy);

    public abstract DynamicScenarioBuilder withExtension(Extension extension);

    public abstract DynamicScenario build();

}
