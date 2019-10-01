package com.yoti.api.client.docs.session.create.check;

/**
 * The configuration applied when creating a LivenessCheck
 */
public interface RequestedLivenessConfig extends RequestedCheckConfig {

    /**
     * Returns the maximum number of retries allowed by the user
     * for a given liveness check
     *
     * @return the maximum number of retries
     */
    int getMaxRetries();

    /**
     * Returns the type of the liveness check
     *
     * @return the liveness type
     */
    String getLivenessType();

}
