package com.yoti.api.client.docs.session.create.check.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedExactMatchingStrategy extends RequestedCaMatchingStrategy {

    @JsonProperty("exact_match")
    public boolean isExactMatch() {
        return true;
    }

    public static RequestedExactMatchingStrategy.Builder builder() {
        return new RequestedExactMatchingStrategy.Builder();
    }

    public static class Builder {

        private Builder() {}

        public RequestedExactMatchingStrategy build() {
            return new RequestedExactMatchingStrategy();
        }
    }
}
