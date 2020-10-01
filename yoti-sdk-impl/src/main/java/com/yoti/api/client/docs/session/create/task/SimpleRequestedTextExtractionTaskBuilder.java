package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedTextExtractionTaskBuilder implements RequestedTextExtractionTaskBuilder {

    private String manualCheck;
    private String chipData;

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
    public RequestedTextExtractionTaskBuilder withChipDataDesired() {
        this.chipData = DocScanConstants.DESIRED;
        return this;
    }

    @Override
    public RequestedTextExtractionTaskBuilder withChipDataIgnore() {
        this.chipData = DocScanConstants.IGNORE;
        return this;
    }

    @Override
    public RequestedTextExtractionTask build() {
        RequestedTextExtractionTaskConfig config = new SimpleRequestedTextExtractionTaskConfig(manualCheck, chipData);
        return new SimpleRequestedTextExtractionTask(config);
    }

}
