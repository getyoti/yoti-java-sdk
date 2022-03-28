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

    @JsonProperty(Property.CALLBACK_ENDPOINT)
    private final String callbackEndpoint;

    @JsonProperty(Property.POLICY)
    private final DynamicPolicy dynamicPolicy;

    @JsonProperty(Property.EXTENSIONS)
    private final List<Extension<?>> extensions;

    @JsonProperty(Property.SUBJECT)
    private final Object subject;

    DynamicScenario(
            String callbackEndpoint,
            DynamicPolicy dynamicPolicy,
            List<Extension<?>> extensions,
            Object subject) {
        this.callbackEndpoint = callbackEndpoint;
        this.dynamicPolicy = dynamicPolicy;
        this.extensions = extensions;
        this.subject = subject;
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

    /**
     * The subject for which the identity assertion will be performed.
     */
    public Object subject() {
        return subject;
    }

    public static class Builder {

        private String callbackEndpoint;
        private DynamicPolicy dynamicPolicy;
        private final List<Extension<?>> extensions = new ArrayList<>();
        private Object subject;

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

        public Builder withSubject(Object subject) {
            this.subject = subject;
            return this;
        }

        public DynamicScenario build() {
            return new DynamicScenario(callbackEndpoint, dynamicPolicy, extensions, subject);
        }

    }

    private static final class Property {

        private static final String POLICY = "policy";
        private static final String SUBJECT = "subject";
        private static final String EXTENSIONS = "extensions";
        private static final String CALLBACK_ENDPOINT = "callback_endpoint";

        private Property() { }

    }

}
