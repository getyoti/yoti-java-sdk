package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see DynamicScenario
 *
 */
public class SimpleDynamicScenario implements DynamicScenario {

    @JsonProperty("callback_endpoint")
    private final String callbackEndpoint;

    @JsonProperty("dynamicPolicy")
    private final DynamicPolicy dynamicPolicy;

    @JsonProperty("extensions")
    private final List<Extension> extensions;

    public SimpleDynamicScenario(String callbackEndpoint, DynamicPolicy dynamicPolicy, List<Extension> extensions) {
        this.callbackEndpoint = callbackEndpoint;
        this.dynamicPolicy = dynamicPolicy;
        this.extensions = extensions;
    }

    /**
     * @see DynamicScenario#callbackEndpoint()
     *
     */
    @Override
    public String callbackEndpoint() {
        return callbackEndpoint;
    }

    /**
     * @see DynamicScenario#policy()
     *
     */
    @Override
    public DynamicPolicy policy() {
        return dynamicPolicy;
    }

    /**
     * @see DynamicScenario#extensions()
     *
     */
    @Override
    public List<Extension> extensions() {
        return extensions;
    }

}
