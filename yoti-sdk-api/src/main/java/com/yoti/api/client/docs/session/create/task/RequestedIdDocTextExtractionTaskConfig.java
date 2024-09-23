package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating each TextExtractionTask
 */
public class RequestedIdDocTextExtractionTaskConfig implements RequestedTaskConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    @JsonProperty("chip_data")
    private final String chipData;

    @JsonProperty("create_expanded_document_fields")
    private final Boolean createExpandedDocumentFields;

    RequestedIdDocTextExtractionTaskConfig(String manualCheck, String chipData, Boolean createExpandedDocumentFields) {
        this.manualCheck = manualCheck;
        this.chipData = chipData;
        this.createExpandedDocumentFields = createExpandedDocumentFields;
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

    /**
     * Describes if expanded document fields should be created for a text-extraction task
     * in the session.
     *
     * @return if expanded document fields should be created
     */
    public Boolean getCreateExpandedDocumentFields() {
        return createExpandedDocumentFields;
    }

}
