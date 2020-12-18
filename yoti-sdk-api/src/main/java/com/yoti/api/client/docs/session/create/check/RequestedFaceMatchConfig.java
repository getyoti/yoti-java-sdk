package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating a FaceMatchCheck
 */
public class RequestedFaceMatchConfig implements RequestedCheckConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    RequestedFaceMatchConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    /**
     * Returns the value for a manual check for a given
     * face match.
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

}
