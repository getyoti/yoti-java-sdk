package com.yoti.api.client.docs.session.create.task;

import com.yoti.api.client.docs.DocScanConstants;

/**
 * Requests that a TextExtractionTask be applied to each Document
 */
public class RequestedIdDocTextExtractionTask extends RequestedTask<RequestedIdDocTextExtractionTaskConfig> {

    private final RequestedIdDocTextExtractionTaskConfig config;

    RequestedIdDocTextExtractionTask(RequestedIdDocTextExtractionTaskConfig config) {
        this.config = config;
    }

    public static RequestedIdDocTextExtractionTask.Builder builder() {
        return new RequestedIdDocTextExtractionTask.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT_TEXT_DATA_EXTRACTION;
    }

    @Override
    public RequestedIdDocTextExtractionTaskConfig getConfig() {
        return config;
    }

    public static class Builder {

        private String manualCheck;
        private String chipData;

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

        public Builder withChipDataDesired() {
            this.chipData = DocScanConstants.DESIRED;
            return this;
        }

        public Builder withChipDataIgnore() {
            this.chipData = DocScanConstants.IGNORE;
            return this;
        }

        public RequestedIdDocTextExtractionTask build() {
            RequestedIdDocTextExtractionTaskConfig config = new RequestedIdDocTextExtractionTaskConfig(manualCheck, chipData);
            return new RequestedIdDocTextExtractionTask(config);
        }
        
    }

}
