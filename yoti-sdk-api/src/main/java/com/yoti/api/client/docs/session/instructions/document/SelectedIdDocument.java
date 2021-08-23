package com.yoti.api.client.docs.session.instructions.document;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an {@code ID_DOCUMENT} that will be used to satisfy a document requirement
 * in a given IDV session.
 */
public class SelectedIdDocument extends SelectedDocument {

    @JsonProperty("country_code")
    private final String countryCode;

    @JsonProperty("document_type")
    private final String documentType;

    private SelectedIdDocument(String countryCode, String documentType) {
        super(DocScanConstants.ID_DOCUMENT);
        this.countryCode = countryCode;
        this.documentType = documentType;
    }

    public static SelectedIdDocument.Builder builder() {
        return new SelectedIdDocument.Builder();
    }

    /**
     * The country code of the selected document
     *
     * @return the country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * The document type of the selected document
     *
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }

    public static class Builder {

        private String countryCode;
        private String documentType;

        private Builder() {}

        /**
         * Sets the country code of the {@link SelectedIdDocument}
         *
         * @param countryCode the country code
         * @return the builder
         */
        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * Sets the document type of the {@link SelectedIdDocument}
         *
         * @param documentType the document type
         * @return the builder
         */
        public Builder withDocumentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public SelectedIdDocument build() {
            return new SelectedIdDocument(countryCode, documentType);
        }

    }

}
