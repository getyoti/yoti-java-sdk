package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedSupplementaryDocTextExtractionTaskBuilder implements RequestedSupplementaryDocTextExtractionTaskBuilder {

    private String manualCheck;

    @Override
    public RequestedSupplementaryDocTextExtractionTaskBuilder withManualCheckAlways() {
        this.manualCheck = DocScanConstants.ALWAYS;
        return this;
    }

    @Override
    public RequestedSupplementaryDocTextExtractionTaskBuilder withManualCheckFallback() {
        this.manualCheck = DocScanConstants.FALLBACK;
        return this;
    }

    @Override
    public RequestedSupplementaryDocTextExtractionTaskBuilder withManualCheckNever() {
        this.manualCheck = DocScanConstants.NEVER;
        return this;
    }

    @Override
    public RequestedSupplementaryDocTextExtractionTask<?> build() {
        RequestedSupplementaryDocTextExtractionTaskConfig config = new SimpleRequestedSupplementaryDocTextExtractionTaskConfig(manualCheck);
        return new SimpleRequestedSupplementaryDocTextExtractionTask(config);
    }
}
