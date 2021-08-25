package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptureResponse {

    @JsonProperty("biometric_consent")
    private String biometricConsent;

    @JsonProperty("required_resources")
    private List<RequiredResourceResponse> requiredResources;

    /**
     * Returns a String enum of the state of biometric consent
     *
     * @return if biometric consent needs to be captured
     */
    public String getBiometricConsent() {
        return biometricConsent;
    }

    /**
     * Returns a list of required resources, in order to satisfy the sessions
     * requirements
     *
     * @return the list of required resources
     */
    public List<RequiredResourceResponse> getRequiredResources() {
        return requiredResources;
    }

}
