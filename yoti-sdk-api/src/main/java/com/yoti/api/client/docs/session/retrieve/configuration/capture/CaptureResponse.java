package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import java.util.List;
import java.util.stream.Collectors;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredIdDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredSupplementaryDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.facecapture.RequiredFaceCaptureResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredStaticLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredZoomLivenessResourceResponse;

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
    public List<RequiredResourceResponse> getResourceRequirements() {
        return requiredResources;
    }

    /**
     * Returns a list of all the document resource requirements (including ID and supplementary documents)
     *
     * @return the document resource requirements
     */
    public List<RequiredDocumentResourceResponse> getDocumentResourceRequirements() {
        return filter(RequiredDocumentResourceResponse.class);
    }

    /**
     * Returns a list of all the ID document resource requirements
     *
     * @return the ID document resource requirements
     */
    public List<RequiredIdDocumentResourceResponse> getIdDocumentResourceRequirements() {
        return filter(RequiredIdDocumentResourceResponse.class);
    }

    /**
     * Returns a list of all the supplementary document resource requirements
     *
     * @return the supplementary document resource requirements
     */
    public List<RequiredSupplementaryDocumentResourceResponse> getSupplementaryResourceRequirements() {
        return filter(RequiredSupplementaryDocumentResourceResponse.class);
    }

    /**
     * Returns a list of all the liveness resource requirements
     *
     * @return the liveness resource requirements
     */
    public List<RequiredLivenessResourceResponse> getLivenessResourceRequirements() {
        return filter(RequiredLivenessResourceResponse.class);
    }

    /**
     * Returns a list of all the zoom liveness resource requirements
     *
     * @return the zoom liveness resource requirements
     */
    public List<RequiredZoomLivenessResourceResponse> getZoomLivenessResourceRequirements() {
        return filter(RequiredZoomLivenessResourceResponse.class);
    }

    /**
     * Returns a list of all the static liveness resource requirements
     *
     * @return the zoom liveness resource requirements
     */
    public List<RequiredStaticLivenessResourceResponse> getStaticLivenessResourceRequirements() {
        return filter(RequiredStaticLivenessResourceResponse.class);
    }

    /**
     * Returns a list of all the Face Capture resource requirements
     *
     * @return the Face Capture resource requirements
     */
    public List<RequiredFaceCaptureResourceResponse> getFaceCaptureResourceRequirements() {
        return filter(RequiredFaceCaptureResourceResponse.class);
    }

    private <T> List<T> filter(Class<T> clazz) {
        return requiredResources.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

}
