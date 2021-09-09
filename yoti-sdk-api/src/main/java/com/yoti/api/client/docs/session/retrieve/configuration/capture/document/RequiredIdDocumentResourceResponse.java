package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequiredIdDocumentResourceResponse extends RequiredDocumentResourceResponse {

    @JsonProperty("supported_countries")
    private List<SupportedCountryResponse> supportedCountries;

    @JsonProperty("allowed_capture_methods")
    private String allowedCaptureMethods;

    @JsonProperty("attempts_remaining")
    private Map<String, Integer> attemptsRemaining;

    /**
     * Returns a list of supported country codes, that can be used
     * to satisfy the requirement.  Each supported country will contain
     * a list of document types that can be used.
     *
     * @return a list of supported countries
     */
    public List<SupportedCountryResponse> getSupportedCountries() {
        return supportedCountries;
    }

    /**
     * Returns the allowed capture method as a String
     *
     * @return the allowed capture method
     */
    public String getAllowedCaptureMethods() {
        return allowedCaptureMethods;
    }

    /**
     * Returns a Map, that is used to track how many attempts are
     * remaining when performing text-extraction.
     *
     * @return the attempts remaining
     */
    public Map<String, Integer> getAttemptsRemaining() {
        return attemptsRemaining;
    }

}
