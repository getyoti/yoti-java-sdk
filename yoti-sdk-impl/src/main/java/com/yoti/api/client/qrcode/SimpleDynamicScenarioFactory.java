package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

public final class SimpleDynamicScenarioFactory implements DynamicScenarioFactory {

    @Override
    public DynamicScenario create(String callbackEndpoint, DynamicPolicy dynamicPolicy, List<Extension> extensions) {
        return new SimpleDynamicScenario(callbackEndpoint, dynamicPolicy, extensions);
    }

}
