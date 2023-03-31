package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating a ProfileDocumentMatchCheck
 */
public class RequestedProfileDocumentMatchConfig implements RequestedCheckConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    RequestedProfileDocumentMatchConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    /**
     * Returns the value for a manual check for a given
     * profile document match check.
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

}
