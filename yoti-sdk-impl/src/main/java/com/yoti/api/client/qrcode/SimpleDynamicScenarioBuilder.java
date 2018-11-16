package com.yoti.api.client.qrcode;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

public class SimpleDynamicScenarioBuilder extends DynamicScenarioBuilder {

    private String callbackEndpoint;
    private DynamicPolicy dynamicPolicy;
    private final List<Extension> extensions = new ArrayList<>();

    @Override
    protected DynamicScenarioBuilder createDynamicScenarioBuilder() {
        return new SimpleDynamicScenarioBuilder();
    }

    @Override
    public DynamicScenarioBuilder withCallbackEndpoint(String callbackEndpoint) {
        this.callbackEndpoint = callbackEndpoint;
        return this;
    }

    @Override
    public DynamicScenarioBuilder withPolicy(DynamicPolicy dynamicPolicy) {
        this.dynamicPolicy = dynamicPolicy;
        return this;
    }

    @Override
    public DynamicScenarioBuilder withExtension(Extension extension) {
        this.extensions.add(extension);
        return this;
    }

    @Override
    public DynamicScenario build() {
        return new SimpleDynamicScenario(callbackEndpoint, dynamicPolicy, extensions);
    }

}
