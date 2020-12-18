package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating a LivenessCheck
 */
public class RequestedLivenessConfig implements RequestedCheckConfig {

    @JsonProperty("liveness_type")
    private final String livenessType;

    @JsonProperty("max_retries")
    private final int maxRetries;

    RequestedLivenessConfig(int maxRetries, String livenessType) {
        this.maxRetries = maxRetries;
        this.livenessType = livenessType;
    }

    /**
     * Returns the maximum number of retries allowed by the user
     * for a given liveness check
     *
     * @return the maximum number of retries
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Returns the type of the liveness check
     *
     * @return the liveness type
     */
    public String getLivenessType() {
        return livenessType;
    }

}
