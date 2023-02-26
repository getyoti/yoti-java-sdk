package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a FaceComparisonCheck
 */
public class RequestedFaceComparisonCheck extends RequestedCheck<RequestedFaceComparisonConfig> {

    private final RequestedFaceComparisonConfig config;

    private RequestedFaceComparisonCheck(RequestedFaceComparisonConfig config) {
        this.config = config;
    }

    public static RequestedFaceComparisonCheck.Builder builder() {
        return new RequestedFaceComparisonCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.FACE_COMPARISON;
    }

    @Override
    public RequestedFaceComparisonConfig getConfig() {
        return config;
    }

    public static class Builder {

        private Builder() {}

        private String manualCheck;

        /**
         * Sets the manual check value to ALWAYS of a config that will be used for face comparison check
         *
         * @return the builder
         */
        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        /**
         * Sets the manual check value to FALLBACK of a config that will be used for face comparison check
         *
         * @return the builder
         */
        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        /**
         * Sets the manual check value to NEVER of a config that will be used for face comparison check
         *
         * @return the builder
         */
        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        public RequestedFaceComparisonCheck build() {
            notNullOrEmpty(manualCheck, "manualCheck");

            RequestedFaceComparisonConfig config = new RequestedFaceComparisonConfig(manualCheck);
            return new RequestedFaceComparisonCheck(config);
        }
    }

}
