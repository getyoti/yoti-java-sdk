package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRequestedFaceMatchConfig implements RequestedFaceMatchConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    public SimpleRequestedFaceMatchConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    @Override
    public String getManualCheck() {
        return manualCheck;
    }

}
