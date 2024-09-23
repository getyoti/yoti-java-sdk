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
        private Boolean createExpandedDocumentFields;

        /**
         * Apply manual_check ALWAYS to all ID_DOCUMENT_TEXT_DATA_EXTRACTION tasks in the session
         * @return the builder
         */
        public Builder withManualCheckAlways() {
            this.manualCheck = DocScanConstants.ALWAYS;
            return this;
        }

        /**
         * Apply manual_check FALLBACK to all ID_DOCUMENT_TEXT_DATA_EXTRACTION tasks in the session
         * @return the builder
         */
        public Builder withManualCheckFallback() {
            this.manualCheck = DocScanConstants.FALLBACK;
            return this;
        }

        /**
         * Apply manual_check NEVER to all ID_DOCUMENT_TEXT_DATA_EXTRACTION tasks in the session
         * @return the builder
         */
        public Builder withManualCheckNever() {
            this.manualCheck = DocScanConstants.NEVER;
            return this;
        }

        /**
         * Apply chip_data DESIRED to all ID_DOCUMENT_TEXT_DATA_EXTRACTION tasks in the session
         * @return the builder
         */
        public Builder withChipDataDesired() {
            this.chipData = DocScanConstants.DESIRED;
            return this;
        }

        /**
         * Apply chip_data IGNORED to all ID_DOCUMENT_TEXT_DATA_EXTRACTION tasks in the session
         * @return the builder
         */
        public Builder withChipDataIgnore() {
            this.chipData = DocScanConstants.IGNORE;
            return this;
        }

        /**
         * Whether to request the creation of expanded document fields for every ID_DOCUMENT_TEXT_DATA_EXTRACTION task
         * @return the builder
         */
        public Builder withCreateExpandedDocumentFields(boolean value) {
            this.createExpandedDocumentFields = value;
            return this;
        }

        public RequestedIdDocTextExtractionTask build() {
            RequestedIdDocTextExtractionTaskConfig config = new RequestedIdDocTextExtractionTaskConfig(manualCheck, chipData, createExpandedDocumentFields);
            return new RequestedIdDocTextExtractionTask(config);
        }
        
    }

}
