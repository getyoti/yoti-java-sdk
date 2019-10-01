package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedDocumentAuthenticityCheck
        extends SimpleRequestedCheck<RequestedDocumentAuthenticityConfig>
        implements RequestedDocumentAuthenticityCheck<RequestedDocumentAuthenticityConfig> {

    private final RequestedDocumentAuthenticityConfig config;

    SimpleRequestedDocumentAuthenticityCheck(RequestedDocumentAuthenticityConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_AUTHENTICITY;
    }

    @Override
    public RequestedDocumentAuthenticityConfig getConfig() {
        return config;
    }

}
