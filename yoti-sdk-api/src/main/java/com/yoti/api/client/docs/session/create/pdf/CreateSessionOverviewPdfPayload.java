package com.yoti.api.client.docs.session.create.pdf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSessionOverviewPdfPayload {

    @JsonProperty("options")
    private final CreateSessionOverviewPdfOptions options;

    private CreateSessionOverviewPdfPayload(CreateSessionOverviewPdfOptions options) {
        this.options = options;
    }

    public static CreateSessionOverviewPdfPayload.Builder builder() {
        return new CreateSessionOverviewPdfPayload.Builder();
    }

    public CreateSessionOverviewPdfOptions getOptions() {
        return options;
    }

    public static class Builder {

        private CreateSessionOverviewPdfOptions options;

        private Builder() { }

        /**
         * Sets the options describing what should be included in the PDF.
         *
         * @param options the {@link CreateSessionOverviewPdfOptions}
         * @return the builder
         */
        public Builder withOptions(CreateSessionOverviewPdfOptions options) {
            this.options = options;
            return this;
        }

        public CreateSessionOverviewPdfPayload build() {
            return new CreateSessionOverviewPdfPayload(options);
        }

    }

}
