package com.yoti.api.client.shareurl;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.shareurl.extension.Extension;
import com.yoti.api.client.shareurl.policy.DynamicPolicy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data required when initiating a dynamic share
 */
public class DynamicScenario {

    @JsonProperty("callback_endpoint")
    private final String callbackEndpoint;

    @JsonProperty("policy")
    private final DynamicPolicy dynamicPolicy;

    @JsonProperty("extensions")
    private final List<Extension<?>> extensions;

    DynamicScenario(String callbackEndpoint, DynamicPolicy dynamicPolicy, List<Extension<?>> extensions) {
        this.callbackEndpoint = callbackEndpoint;
        this.dynamicPolicy = dynamicPolicy;
        this.extensions = extensions;
    }

    public static DynamicScenario.Builder builder() {
        return new DynamicScenario.Builder();
    }

    /**
     * The device's callback endpoint. Must be a URL relative to the Application Domain specified in Yoti Hub
     */
    public String callbackEndpoint() {
        return callbackEndpoint;
    }

    /**
     * The customisable {@link DynamicPolicy} to use in the share
     */
    public DynamicPolicy policy() {
        return dynamicPolicy;
    }

    /**
     * List of {@link Extension} to be activated for the application
     */
    public List<Extension<?>> extensions() {
        return extensions;
    }

    public static class Builder {

        private String callbackEndpoint;
        private DynamicPolicy dynamicPolicy;
        private final List<Extension<?>> extensions = new ArrayList<>();

        public Builder withCallbackEndpoint(String callbackEndpoint) {
            this.callbackEndpoint = callbackEndpoint;
            return this;
        }

        public Builder withPolicy(DynamicPolicy dynamicPolicy) {
            this.dynamicPolicy = dynamicPolicy;
            return this;
        }

        public Builder withExtension(Extension<?> extension) {
            this.extensions.add(extension);
            return this;
        }

        public DynamicScenario build() {
            return new DynamicScenario(callbackEndpoint, dynamicPolicy, extensions);
        }

    }

}
