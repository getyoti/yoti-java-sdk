package com.yoti.api.client.identity;

import static com.yoti.api.client.spi.remote.call.HttpMethod.SUPPORTED_HTTP_METHODS;

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.yoti.validation.Validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class MatchNotification {

    @JsonProperty(Property.URL)
    private final String endpoint;

    @JsonProperty(Property.METHOD)
    private final String method;

    @JsonProperty(Property.VERIFY_TLS)
    private final boolean verifyTls;

    @JsonProperty(Property.HEADERS)
    private final Map<String, String> headers;

    private MatchNotification(Builder builder) {
        endpoint = builder.endpoint;
        method = builder.method;
        verifyTls = builder.verifyTls;
        headers = builder.headers;
    }

    public String getEndpoint() {
        return endpoint;
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

    public static Builder forUrl(URI uri) {
        return new Builder(uri);
    }

    public static final class Builder {

        private final String endpoint;
        private final Map<String, String> headers;

        private String method;
        private boolean verifyTls;

        private Builder(URI uri) {
            Validation.notNull(uri, Property.URL);

            endpoint = uri.toString();
            headers = new HashMap<>();
        }

        public Builder withMethod(String method) {
            Validation.withinList(method.toUpperCase(Locale.ROOT), SUPPORTED_HTTP_METHODS);

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

        public MatchNotification build() {
            return new MatchNotification(this);
        }

    }

    private static final class Property {

        private static final String URL = "url";
        private static final String METHOD = "method";
        private static final String VERIFY_TLS = "verifyTls";
        private static final String HEADERS = "headers";

    }

}
