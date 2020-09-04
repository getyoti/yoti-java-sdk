package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedDocumentAuthenticityCheckBuilder implements RequestedDocumentAuthenticityCheckBuilder {

    private String manualCheck;

    @Override
    public SimpleRequestedDocumentAuthenticityCheckBuilder withManualCheckAlways() {
        this.manualCheck = DocScanConstants.ALWAYS;
        return this;
    }

    @Override
    public SimpleRequestedDocumentAuthenticityCheckBuilder withManualCheckFallback() {
        this.manualCheck = DocScanConstants.FALLBACK;
        return this;
    }

    @Override
    public SimpleRequestedDocumentAuthenticityCheckBuilder withManualCheckNever() {
        this.manualCheck = DocScanConstants.NEVER;
        return this;
    }

    @Override
    public RequestedDocumentAuthenticityCheck build() {
        RequestedDocumentAuthenticityConfig config = new SimpleRequestedDocumentAuthenticityConfig(manualCheck);
        return new SimpleRequestedDocumentAuthenticityCheck(config);
    }

}
