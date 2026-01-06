package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.create.filters.DocumentFilter;

/**
 * Requests creation of a DocumentAuthenticityCheck
 */
public class RequestedDocumentAuthenticityCheck extends RequestedCheck<RequestedDocumentAuthenticityConfig> {

    private final RequestedDocumentAuthenticityConfig config;

    private RequestedDocumentAuthenticityCheck(RequestedDocumentAuthenticityConfig config) {
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
        private IssuingAuthoritySubCheck issuingAuthoritySubCheck;

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

        public Builder withIssuingAuthoritySubCheck() {
            this.issuingAuthoritySubCheck = IssuingAuthoritySubCheck.builder()
                    .withRequested(true)
                    .build();
            return this;
        }

        public Builder withIssuingAuthoritySubCheck(DocumentFilter documentFilter) {
            this.issuingAuthoritySubCheck = IssuingAuthoritySubCheck.builder()
                    .withRequested(true)
                    .withDocumentFilter(documentFilter)
                    .build();
            return this;
        }

        public RequestedDocumentAuthenticityCheck build() {
            RequestedDocumentAuthenticityConfig config = new RequestedDocumentAuthenticityConfig(manualCheck, issuingAuthoritySubCheck);
            return new RequestedDocumentAuthenticityCheck(config);
        }
    }

}
