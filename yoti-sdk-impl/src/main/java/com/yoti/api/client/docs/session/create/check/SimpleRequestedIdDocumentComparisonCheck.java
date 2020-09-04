package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedIdDocumentComparisonCheck
        extends SimpleRequestedCheck<RequestedIdDocumentComparisonConfig>
        implements RequestedIdDocumentComparisonCheck<RequestedIdDocumentComparisonConfig> {

    private final RequestedIdDocumentComparisonConfig config;

    SimpleRequestedIdDocumentComparisonCheck(RequestedIdDocumentComparisonConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_COMPARISON;
    }

    @Override
    public RequestedIdDocumentComparisonConfig getConfig() {
        return config;
    }
}
