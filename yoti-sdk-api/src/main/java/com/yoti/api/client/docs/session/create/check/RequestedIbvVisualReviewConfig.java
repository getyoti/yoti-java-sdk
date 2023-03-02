package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating an IbvVisualCheck
 */
public class RequestedIbvVisualReviewConfig implements RequestedCheckConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    RequestedIbvVisualReviewConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    /**
     * Returns the value for a manual check for a given
     * ibv visual check.
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

}
