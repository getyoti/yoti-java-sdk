package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SimpleRequestedDocumentAuthenticityConfig implements RequestedDocumentAuthenticityConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    public SimpleRequestedDocumentAuthenticityConfig(String manualCheck) {
        this.manualCheck = manualCheck;
    }

    @Override
    public String getManualCheck() {
        return manualCheck;
    }

}
