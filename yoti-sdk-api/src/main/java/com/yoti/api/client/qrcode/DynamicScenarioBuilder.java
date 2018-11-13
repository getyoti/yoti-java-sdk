package com.yoti.api.client.qrcode;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

public class DynamicScenarioBuilder {

    private String callbackEndpoint;
    private DynamicPolicy dynamicPolicy;
    private final List<Extension> extensions = new ArrayList<>(); // FIXME: Should this be a list?  Or a map?

    public DynamicScenarioBuilder withCallbackEndpoint(String callbackEndpoint) {
        this.callbackEndpoint = callbackEndpoint;
        return this;
    }

    public DynamicScenarioBuilder withPolicy(DynamicPolicy dynamicPolicy) {
        this.dynamicPolicy = dynamicPolicy;
        return this;
    }

    public DynamicScenarioBuilder withExtension(Extension extension) {
        this.extensions.add(extension);
        return this;
    }

    public DynamicScenario build() {
        ServiceLoader<DynamicScenarioFactory> factoryLoader = ServiceLoader.load(DynamicScenarioFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of DynamicScenarioFactory");
        }
        DynamicScenarioFactory dynamicScenarioFactory = factoryLoader.iterator().next();
        return dynamicScenarioFactory.create(callbackEndpoint, dynamicPolicy, extensions);
    }

}
