package com.yoti.api.client.docs.session.create.check;

/**
 * Builder to assist the creation of {@link RequestedLivenessCheck}.
 */
public interface RequestedLivenessCheckBuilder {

    /**
     * Sets the type to be of a ZOOM liveness check
     *
     * @return the builder
     */
    RequestedLivenessCheckBuilder forZoomLiveness();

    /**
     * Sets the type of the liveness check to the supplied value
     *
     * @param livenessType the type of the liveness check
     * @return the builder
     */
    RequestedLivenessCheckBuilder forLivenessType(String livenessType);

    /**
     * Sets the maximum number of retries allowed by the user
     *
     * @param maxRetries the maximum number of retries
     * @return the builder
     */
    RequestedLivenessCheckBuilder withMaxRetries(int maxRetries);

    RequestedLivenessCheck build();

}
