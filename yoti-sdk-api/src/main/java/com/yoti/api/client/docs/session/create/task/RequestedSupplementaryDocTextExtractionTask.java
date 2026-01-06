package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedSupplementaryDocTextExtractionTask extends RequestedTask<RequestedSupplementaryDocTextExtractionTaskConfig> {

    @JsonProperty("config")
    private final RequestedSupplementaryDocTextExtractionTaskConfig config;

    private RequestedSupplementaryDocTextExtractionTask(RequestedSupplementaryDocTextExtractionTaskConfig config) {
        this.config = config;
    }

    public static RequestedSupplementaryDocTextExtractionTask.Builder builder() {
        return new RequestedSupplementaryDocTextExtractionTask.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_EXTRACTION;
    }

    @Override
    public RequestedSupplementaryDocTextExtractionTaskConfig getConfig() {
        return config;
    }

    public static class Builder {

        private String manualCheck;

        private Builder() {}

        /**
         * Requires that the Task is always followed by a manual TextDataCheck
         *
         * @return the builder
         */
        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        /**
         * Requires that only failed Tasks are followed by a manual TextDataCheck
         *
         * @return the builder
         */
        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        /**
         * The TextExtractionTask will never fallback to a manual TextDataCheck
         *
         * @return the builder
         */
        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        public RequestedSupplementaryDocTextExtractionTask build() {
            RequestedSupplementaryDocTextExtractionTaskConfig config = new RequestedSupplementaryDocTextExtractionTaskConfig(manualCheck);
            return new RequestedSupplementaryDocTextExtractionTask(config);
        }
        
    }

}
