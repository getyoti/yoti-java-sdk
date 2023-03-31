package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a DocumentSchemeValidityCheck
 */
public class RequestedDocumentSchemeValidityCheck extends RequestedCheck<RequestedDocumentSchemeValidityConfig> {

    private final RequestedDocumentSchemeValidityConfig config;

    RequestedDocumentSchemeValidityCheck(RequestedDocumentSchemeValidityConfig config) {
        this.config = config;
    }

    public static RequestedDocumentSchemeValidityCheck.Builder builder() {
        return new RequestedDocumentSchemeValidityCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.DOCUMENT_SCHEME_VALIDITY_CHECK;
    }

    @Override
    public RequestedDocumentSchemeValidityConfig getConfig() {
        return config;
    }

    public static class Builder {

        private Builder() { }

        private String manualCheck;
        private String scheme;

        /**
         * Sets the manual check value to ALWAYS of a config that will be used for document scheme validity check
         *
         * @return the builder
         */
        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        /**
         * Sets the manual check value to FALLBACK of a config that will be used for document scheme validity check
         *
         * @return the builder
         */
        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        /**
         * Sets the manual check value to NEVER of a config that will be used for document scheme validity check
         *
         * @return the builder
         */
        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        /**
         * Sets the scheme value of a config that will be used for document scheme validity check
         *
         * @return the builder
         */
        public Builder withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public RequestedDocumentSchemeValidityCheck build() {
            notNullOrEmpty(manualCheck, "manualCheck");
            notNullOrEmpty(scheme, "scheme");

            RequestedDocumentSchemeValidityConfig config = new RequestedDocumentSchemeValidityConfig(manualCheck, scheme);
            return new RequestedDocumentSchemeValidityCheck(config);
        }
    }

}
