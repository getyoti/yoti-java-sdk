package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating each SupplementaryTextExtractionTask
 */
public class RequestedSupplementaryDocTextExtractionTaskConfig implements RequestedTaskConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    RequestedSupplementaryDocTextExtractionTaskConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    /**
     * Describes the manual fallback behaviour applied to each Task
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

}
