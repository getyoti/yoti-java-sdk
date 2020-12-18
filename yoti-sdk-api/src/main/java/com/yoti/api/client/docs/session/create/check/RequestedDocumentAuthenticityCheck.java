package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a DocumentAuthenticityCheck
 */
public class RequestedDocumentAuthenticityCheck extends RequestedCheck<RequestedDocumentAuthenticityConfig> {

    private final RequestedDocumentAuthenticityConfig config;

    RequestedDocumentAuthenticityCheck(RequestedDocumentAuthenticityConfig config) {
        this.config = config;
    }

    public static RequestedDocumentAuthenticityCheck.Builder builder() {
        return new RequestedDocumentAuthenticityCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_AUTHENTICITY;
    }

    @Override
    public RequestedDocumentAuthenticityConfig getConfig() {
        return config;
    }

    public static class Builder {

        private String manualCheck;

        private Builder() {}

        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        public RequestedDocumentAuthenticityCheck build() {
            RequestedDocumentAuthenticityConfig config = new RequestedDocumentAuthenticityConfig(manualCheck);
            return new RequestedDocumentAuthenticityCheck(config);
        }
    }

}
