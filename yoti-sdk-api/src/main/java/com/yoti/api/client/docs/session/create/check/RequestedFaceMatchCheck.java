package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests creation of a FaceMatchCheck
 */
public class RequestedFaceMatchCheck extends RequestedCheck<RequestedFaceMatchConfig> {

    private final RequestedFaceMatchConfig config;

    RequestedFaceMatchCheck(RequestedFaceMatchConfig config) {
        this.config = config;
    }

    public static RequestedFaceMatchCheck.Builder builder() {
        return new RequestedFaceMatchCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_FACE_MATCH;
    }

    @Override
    public RequestedFaceMatchConfig getConfig() {
        return config;
    }

    public static class Builder {

        private String manualCheck;

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

        public RequestedFaceMatchCheck build() {
            notNullOrEmpty(manualCheck, "manualCheck");

            RequestedFaceMatchConfig config = new RequestedFaceMatchConfig(manualCheck);
            return new RequestedFaceMatchCheck(config);
        }
    }

}
