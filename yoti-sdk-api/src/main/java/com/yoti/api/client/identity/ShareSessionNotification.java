package com.yoti.api.client.identity;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.yoti.validation.Validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ShareSessionNotification {

    @JsonProperty(Property.URL)
    private final String url;

    @JsonProperty(Property.METHOD)
    private final String method;

    @JsonProperty(Property.VERIFY_TLS)
    private final boolean verifyTls;

    @JsonProperty(Property.HEADERS)
    private final Map<String, String> headers;

    private ShareSessionNotification(Builder builder) {
        url = builder.url;
        method = builder.method;
        verifyTls = builder.verifyTls;
        headers = builder.headers;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public boolean isVerifyTls() {
        return verifyTls;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static Builder builder(URI uri) {
        return new Builder(uri);
    }

    public static final class Builder {

        private final String url;
        private final Map<String, String> headers;

        private String method;
        private boolean verifyTls;

        private Builder(URI uri) {
            url = uri.toString();
            method = "POST";
            verifyTls = true;
            headers = new HashMap<>();
        }

        public Builder withMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder withVerifyTls(boolean verifyTls) {
            this.verifyTls = verifyTls;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder withHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public ShareSessionNotification build() {
            Validation.notNullOrEmpty(url, Property.URL);

            return new ShareSessionNotification(this);
        }

    }

    private static final class Property {

        private static final String URL = "url";
        private static final String METHOD = "method";
        private static final String VERIFY_TLS = "verifyTls";
        private static final String HEADERS = "headers";

    }

}
