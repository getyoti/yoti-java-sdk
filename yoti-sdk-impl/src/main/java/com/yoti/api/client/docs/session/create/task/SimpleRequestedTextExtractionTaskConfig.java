package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRequestedTextExtractionTaskConfig implements RequestedTextExtractionTaskConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    public SimpleRequestedTextExtractionTaskConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    public String getManualCheck() {
        return manualCheck;
    }

}
