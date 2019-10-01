package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedTextExtractionTaskBuilder implements RequestedTextExtractionTaskBuilder {

    private String manualCheck;

    @Override
    public RequestedTextExtractionTaskBuilder withManualCheckAlways() {
        this.manualCheck = DocScanConstants.ALWAYS;
        return this;
    }

    @Override
    public RequestedTextExtractionTaskBuilder withManualCheckFallback() {
        this.manualCheck = DocScanConstants.FALLBACK;
        return this;
    }

    @Override
    public RequestedTextExtractionTaskBuilder withManualCheckNever() {
        this.manualCheck = DocScanConstants.NEVER;
        return this;
    }

    @Override
    public RequestedTextExtractionTask build() {
        RequestedTextExtractionTaskConfig config = new SimpleRequestedTextExtractionTaskConfig(manualCheck);
        return new SimpleRequestedTextExtractionTask(config);
    }

}
