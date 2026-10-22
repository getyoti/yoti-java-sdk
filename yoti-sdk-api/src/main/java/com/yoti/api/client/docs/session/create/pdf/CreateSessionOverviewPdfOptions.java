package com.yoti.api.client.docs.session.create.pdf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSessionOverviewPdfOptions {

    @JsonProperty("include_images")
    private final Boolean includeImages;

    @JsonProperty("include_extracted_text")
    private final Boolean includeExtractedText;

    @JsonProperty("include_device_tracking")
    private final Boolean includeDeviceTracking;

    private CreateSessionOverviewPdfOptions(Boolean includeImages, Boolean includeExtractedText, Boolean includeDeviceTracking) {
        this.includeImages = includeImages;
        this.includeExtractedText = includeExtractedText;
        this.includeDeviceTracking = includeDeviceTracking;
    }

    public static CreateSessionOverviewPdfOptions.Builder builder() {
        return new CreateSessionOverviewPdfOptions.Builder();
    }

    public Boolean getIncludeImages() {
        return includeImages;
    }

    public Boolean getIncludeExtractedText() {
        return includeExtractedText;
    }

    public Boolean getIncludeDeviceTracking() {
        return includeDeviceTracking;
    }

    public static class Builder {

        private Boolean includeImages;
        private Boolean includeExtractedText;
        private Boolean includeDeviceTracking;

        private Builder() { }

        /**
         * Sets whether images should be included in the PDF.
         *
         * @param includeImages whether to include images
         * @return the builder
         */
        public Builder withIncludeImages(boolean includeImages) {
            this.includeImages = includeImages;
            return this;
        }

        /**
         * Sets whether extracted text should be included in the PDF.
         *
         * @param includeExtractedText whether to include extracted text
         * @return the builder
         */
        public Builder withIncludeExtractedText(boolean includeExtractedText) {
            this.includeExtractedText = includeExtractedText;
            return this;
        }

        /**
         * Sets whether device tracking information should be included in the PDF.
         *
         * @param includeDeviceTracking whether to include device tracking information
         * @return the builder
         */
        public Builder withIncludeDeviceTracking(boolean includeDeviceTracking) {
            this.includeDeviceTracking = includeDeviceTracking;
            return this;
        }

        public CreateSessionOverviewPdfOptions build() {
            return new CreateSessionOverviewPdfOptions(includeImages, includeExtractedText, includeDeviceTracking);
        }

    }

}
