package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating each TextExtractionTask
 */
public class RequestedTextExtractionTaskConfig implements RequestedTaskConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    @JsonProperty("chip_data")
    private final String chipData;

    RequestedTextExtractionTaskConfig(String manualCheck, String chipData) {
        this.manualCheck = manualCheck;
        this.chipData = chipData;
    }

    /**
     * Describes the manual fallback behaviour applied to each Task
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

    /**
     * Describes how to use chip data from an ID document if
     * it is available
     *
     * @return the chip data usage
     */
    public String getChipData() {
        return chipData;
    }

}
