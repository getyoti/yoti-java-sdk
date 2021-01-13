package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link RequestedCheck} requests creation of a Check to be performed on a document
 *
 * @param <T> class that implements {@link RequestedCheckConfig}
 */
public abstract class RequestedCheck<T extends RequestedCheckConfig> {

    /**
     * Return the type of the Check to create
     *
     * @return the check type
     */
    @JsonProperty("type")
    public abstract String getType();

    /**
     * Return configuration to apply to the Check
     *
     * @return the configuration
     */
    @JsonProperty("config")
    public abstract T getConfig();

    public static RequestedDocumentAuthenticityCheck.Builder forDocumentAuthenticity() {
        return RequestedDocumentAuthenticityCheck.builder();
    }

    public static RequestedFaceMatchCheck.Builder forFaceMatch() {
        return RequestedFaceMatchCheck.builder();
    }

    public static RequestedIdDocumentComparisonCheck.Builder forIdDocumentComparison() {
        return RequestedIdDocumentComparisonCheck.builder();
    }

    public static RequestedLivenessCheck.Builder forLiveness() {
        return RequestedLivenessCheck.builder();
    }

    public static RequestedThirdPartyIdentityCheck.Builder forThirdPartyIdentity() {
        return RequestedThirdPartyIdentityCheck.builder();
    }

}
