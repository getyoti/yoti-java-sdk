package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class RequestedIdDocumentComparisonCheck extends RequestedCheck<RequestedIdDocumentComparisonConfig> {

    private final RequestedIdDocumentComparisonConfig config;

    RequestedIdDocumentComparisonCheck(RequestedIdDocumentComparisonConfig config) {
        this.config = config;
    }

    public static RequestedIdDocumentComparisonCheck.Builder builder() {
        return new RequestedIdDocumentComparisonCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_COMPARISON;
    }

    @Override
    public RequestedIdDocumentComparisonConfig getConfig() {
        return config;
    }

    public static class Builder {

        private Builder() {
        }

        public RequestedIdDocumentComparisonCheck build() {
            return new RequestedIdDocumentComparisonCheck(new RequestedIdDocumentComparisonConfig());
        }

    }

}
