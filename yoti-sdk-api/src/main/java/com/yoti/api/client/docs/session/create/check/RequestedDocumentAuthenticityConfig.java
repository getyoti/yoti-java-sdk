package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating a DocumentAuthenticityCheck
 */
public class RequestedDocumentAuthenticityConfig implements RequestedCheckConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    public RequestedDocumentAuthenticityConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    /**
     * Returns the value for a manual check for a given
     * document authenticity check.
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

}
