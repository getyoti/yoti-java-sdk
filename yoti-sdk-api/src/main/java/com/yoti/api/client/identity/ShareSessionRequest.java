package com.yoti.api.client.identity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.identity.extension.Extension;
import com.yoti.api.client.identity.policy.Policy;
import com.yoti.validation.Validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareSessionRequest {

    @JsonProperty(Property.SUBJECT)
    private final Map<String, Object> subject;

    @JsonProperty(Property.POLICY)
    private final Policy policy;

    @JsonProperty(Property.EXTENSIONS)
    private final List<Extension<?>> extensions;

    @JsonProperty(Property.REDIRECT_URI)
    private final String redirectUri;

    @JsonProperty(Property.NOTIFICATION)
    private final ShareSessionNotification notification;

    private ShareSessionRequest(Builder builder) {
        subject = builder.subject;
        policy = builder.policy;
        extensions = builder.extensions;
        redirectUri = builder.redirectUri;
        notification = builder.notification;
    }

    public Map<String, Object> getSubject() {
        return subject;
    }

    public Policy getPolicy() {
        return policy;
    }

    public List<Extension<?>> getExtensions() {
        return extensions;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public ShareSessionNotification getNotification() {
        return notification;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Map<String, Object> subject;
        private Policy policy;
        private List<Extension<?>> extensions;
        private String redirectUri;
        private ShareSessionNotification notification;

        private Builder() {
            extensions = new ArrayList<>();
        }

        public Builder withSubject(Map<String, Object> subject) {
            this.subject = subject;
            return this;
        }

        public Builder withPolicy(Policy policy) {
            this.policy = policy;
            return this;
        }

        public Builder withExtensions(List<Extension<?>> extensions) {
            this.extensions = Collections.unmodifiableList(extensions);
            return this;
        }

        public Builder withExtension(Extension<?> extension) {
            extensions.add(extension);
            return this;
        }

        public Builder withRedirectUri(URI redirectUri) {
            this.redirectUri = redirectUri.toString();
            return this;
        }

        public Builder withNotification(ShareSessionNotification notification) {
            this.notification = notification;
            return this;
        }

        public ShareSessionRequest build() {
            Validation.notNull(policy, Property.POLICY);
            Validation.notNullOrEmpty(redirectUri, Property.REDIRECT_URI);

            return new ShareSessionRequest(this);
        }

    }

    private static final class Property {

        private static final String SUBJECT = "subject";
        private static final String POLICY = "policy";
        private static final String EXTENSIONS = "extensions";
        private static final String REDIRECT_URI = "redirectUri";
        private static final String NOTIFICATION = "notification";

    }

}
