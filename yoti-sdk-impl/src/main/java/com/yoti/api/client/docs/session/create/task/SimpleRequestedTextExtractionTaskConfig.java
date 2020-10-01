package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleRequestedTextExtractionTaskConfig implements RequestedTextExtractionTaskConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    @JsonProperty("chip_data")
    private final String chipData;

    public SimpleRequestedTextExtractionTaskConfig(String manualCheck, String chipData) {
        this.manualCheck = manualCheck;
        this.chipData = chipData;
    }

    public String getManualCheck() {
        return manualCheck;
    }

    @Override
    public String getChipData() {
        return chipData;
    }

}
