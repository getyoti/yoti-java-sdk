package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.Policy;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SimpleDynamicScenario implements DynamicScenario {

    /**
     *
     */
    @JsonProperty("callback_endpoint")
    private final String callbackEndpoint;

    /**
     *
     */
    @JsonProperty("policy")
    private final Policy policy;

    /**
     *
     */
    @JsonProperty("extensions")
    private final List<Extension> extensions;

    public SimpleDynamicScenario(String callbackEndpoint, Policy policy, List<Extension> extensions) {
        this.callbackEndpoint = callbackEndpoint;
        this.policy = policy;
        this.extensions = extensions;
    }

    @Override
    public String callbackEndpoint() {
        return callbackEndpoint;
    }

    @Override
    public Policy policy() {
        return policy;
    }

    @Override
    public List<Extension> extensions() {
        return extensions;
    }

}
