package com.yoti.api.client.docs.session.create.check;

/**
 * Requests creation of a LivenessCheck
 *
 * @param <T> class extending {@link RequestedLivenessConfig}
 */
public interface RequestedLivenessCheck<T extends RequestedLivenessConfig> extends RequestedCheck<RequestedLivenessConfig> {

    @Override
    RequestedLivenessConfig getConfig();

}
