package com.yoti.api.client.sandbox.docs.request.check.report;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxRecommendation {

    @JsonProperty("value")
    private final String value;

    @JsonProperty("reason")
    private final String reason;

    @JsonProperty("recovery_suggestion")
    private final String recoverySuggestion;

    SandboxRecommendation(String value, String reason, String recoverySuggestion) {
        this.value = value;
        this.reason = reason;
        this.recoverySuggestion = recoverySuggestion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public String getRecoverySuggestion() {
        return recoverySuggestion;
    }

    public static class Builder {

        private String value;
        private String reason;
        private String recoverySuggestion;

        private Builder() {}

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder withRecoverySuggestion(String recoverySuggestion) {
            this.recoverySuggestion = recoverySuggestion;
            return this;
        }

        public SandboxRecommendation build() {
            notNull(value, "value");

            return new SandboxRecommendation(value, reason, recoverySuggestion);
        }

    }
}
