package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a IbvVisualReviewCheck
 */
public class RequestedIbvVisualReviewCheck extends RequestedCheck<RequestedIbvVisualReviewConfig> {

    private final RequestedIbvVisualReviewConfig config;

    RequestedIbvVisualReviewCheck(RequestedIbvVisualReviewConfig config) {
        this.config = config;
    }

    public static RequestedIbvVisualReviewCheck.Builder builder() {
        return new RequestedIbvVisualReviewCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.IBV_VISUAL_REVIEW_CHECK;
    }

    @Override
    public RequestedIbvVisualReviewConfig getConfig() {
        return config;
    }

    public static class Builder {

        private Builder() { }

        private String manualCheck;

        /**
         * Sets the manual check value to ALWAYS of a config that will be used for ibv visual review check
         *
         * @return the builder
         */
        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        /**
         * Sets the manual check value to FALLBACK of a config that will be used for ibv visual review check
         *
         * @return the builder
         */
        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        /**
         * Sets the manual check value to NEVER of a config that will be used for ibv visual review check
         *
         * @return the builder
         */
        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        public RequestedIbvVisualReviewCheck build() {
            notNullOrEmpty(manualCheck, "manualCheck");

            RequestedIbvVisualReviewConfig config = new RequestedIbvVisualReviewConfig(manualCheck);
            return new RequestedIbvVisualReviewCheck(config);
        }
    }

}
