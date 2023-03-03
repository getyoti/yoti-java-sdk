package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a ProfileDocumentMatchCheck
 */
public class RequestedProfileDocumentMatchCheck extends RequestedCheck<RequestedProfileDocumentMatchConfig> {

    private final RequestedProfileDocumentMatchConfig config;

    RequestedProfileDocumentMatchCheck(RequestedProfileDocumentMatchConfig config) {
        this.config = config;
    }

    public static RequestedProfileDocumentMatchCheck.Builder builder() {
        return new RequestedProfileDocumentMatchCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.PROFILE_DOCUMENT_MATCH;
    }

    @Override
    public RequestedProfileDocumentMatchConfig getConfig() {
        return config;
    }

    public static class Builder {

        private Builder() { }

        private String manualCheck;

        /**
         * Sets the manual check value to ALWAYS of a config that will be used for profile document match check
         *
         * @return the builder
         */
        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        /**
         * Sets the manual check value to FALLBACK of a config that will be used for profile document match check
         *
         * @return the builder
         */
        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        /**
         * Sets the manual check value to NEVER of a config that will be used for profile document match check
         *
         * @return the builder
         */
        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        public RequestedProfileDocumentMatchCheck build() {
            notNullOrEmpty(manualCheck, "manualCheck");

            RequestedProfileDocumentMatchConfig config = new RequestedProfileDocumentMatchConfig(manualCheck);
            return new RequestedProfileDocumentMatchCheck(config);
        }
    }

}
