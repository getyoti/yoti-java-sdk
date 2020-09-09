package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRequestedSupplementaryDocTextExtractionTaskConfig implements RequestedSupplementaryDocTextExtractionTaskConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    public SimpleRequestedSupplementaryDocTextExtractionTaskConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    @Override
    public String getManualCheck() {
        return manualCheck;
    }
}
