package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRequestedLivenessConfig implements RequestedLivenessConfig {

    @JsonProperty("liveness_type")
    private final String livenessType;

    @JsonProperty("max_retries")
    private int maxRetries;

    SimpleRequestedLivenessConfig(int maxRetries, String livenessType) {
        this.maxRetries = maxRetries;
        this.livenessType = livenessType;
    }

    @Override
    public int getMaxRetries() {
        return maxRetries;
    }

    @Override
    public String getLivenessType() {
        return livenessType;
    }

}
