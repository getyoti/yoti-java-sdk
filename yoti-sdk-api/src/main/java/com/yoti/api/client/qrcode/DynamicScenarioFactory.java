package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

public interface DynamicScenarioFactory {

    DynamicScenario create(String callbackEndpoint, DynamicPolicy dynamicPolicy, List<Extension> extensions);

}

