package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedTextExtractionTask
        extends SimpleRequestedTask<RequestedTextExtractionTaskConfig>
        implements RequestedTextExtractionTask<RequestedTextExtractionTaskConfig> {

    private final RequestedTextExtractionTaskConfig config;

    public SimpleRequestedTextExtractionTask(RequestedTextExtractionTaskConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_TEXT_DATA_EXTRACTION;
    }

    @Override
    public RequestedTextExtractionTaskConfig getConfig() {
        return config;
    }

}
