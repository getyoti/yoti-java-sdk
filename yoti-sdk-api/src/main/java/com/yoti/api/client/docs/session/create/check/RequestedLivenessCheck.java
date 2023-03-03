package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a LivenessCheck
 */
public class RequestedLivenessCheck extends RequestedCheck<RequestedLivenessConfig> {

    private final RequestedLivenessConfig config;

    RequestedLivenessCheck(RequestedLivenessConfig config) {
        this.config = config;
    }

    public static RequestedLivenessCheck.Builder builder() {
        return new RequestedLivenessCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.LIVENESS;
    }

    @Override
    public RequestedLivenessConfig getConfig() {
        return config;
    }

    public static class Builder {

        private String livenessType;
        private int maxRetries = 1;

        /**
         * Sets the type to be of a ZOOM liveness check
         *
         * @return the builder
         */
        public Builder forZoomLiveness() {
            return forLivenessType(DocScanConstants.ZOOM);
        }

        /**
         * Sets the type to be of a STATIC liveness check
         *
         * @return the builder
         */
        public Builder forStaticLiveness() {
            return forLivenessType(DocScanConstants.STATIC);
        }

        /**
         * Sets the type of the liveness check to the supplied value
         *
         * @param livenessType the type of the liveness check
         * @return the builder
         */
        public Builder forLivenessType(String livenessType) {
            this.livenessType = livenessType;
            return this;
        }

        /**
         * Sets the maximum number of retries allowed by the user
         *
         * @param maxRetries the maximum number of retries
         * @return the builder
         */
        public Builder withMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public RequestedLivenessCheck build() {
            notNullOrEmpty(livenessType, "livenessType");

            RequestedLivenessConfig config = new RequestedLivenessConfig(maxRetries, livenessType);
            return new RequestedLivenessCheck(config);
        }

    }

}
