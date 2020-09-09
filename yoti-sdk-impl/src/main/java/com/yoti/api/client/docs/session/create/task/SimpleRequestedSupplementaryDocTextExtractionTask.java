package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRequestedSupplementaryDocTextExtractionTask
        extends SimpleRequestedTask<RequestedSupplementaryDocTextExtractionTaskConfig>
        implements RequestedSupplementaryDocTextExtractionTask<RequestedSupplementaryDocTextExtractionTaskConfig> {

    @JsonProperty("config")
    private final RequestedSupplementaryDocTextExtractionTaskConfig config;

    public SimpleRequestedSupplementaryDocTextExtractionTask(RequestedSupplementaryDocTextExtractionTaskConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_EXTRACTION;
    }

    @Override
    public RequestedSupplementaryDocTextExtractionTaskConfig getConfig() {
        return config;
    }
}
