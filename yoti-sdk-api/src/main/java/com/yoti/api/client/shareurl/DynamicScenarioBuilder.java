package com.yoti.api.client.shareurl;

import java.util.ServiceLoader;

import com.yoti.api.client.shareurl.extension.Extension;
import com.yoti.api.client.shareurl.policy.DynamicPolicy;

public abstract class DynamicScenarioBuilder {

    public static final DynamicScenarioBuilder newInstance() {
        ServiceLoader<DynamicScenarioBuilder> dynamicScenarioBuilderLoader = ServiceLoader.load(DynamicScenarioBuilder.class);
        if (!dynamicScenarioBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + DynamicScenarioBuilder.class.getSimpleName());
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
