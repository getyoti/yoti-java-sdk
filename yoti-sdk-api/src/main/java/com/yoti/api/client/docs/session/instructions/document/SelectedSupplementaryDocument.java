package com.yoti.api.client.docs.session.instructions.document;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an {@code SUPPLEMENTARY_DOCUMENT} that will be used to satisfy a document requirement
 * in a given IDV session.
 */
public class SelectedSupplementaryDocument extends SelectedDocument {

    @JsonProperty("country_code")
    private final String countryCode;

    @JsonProperty("document_type")
    private final String documentType;

    private SelectedSupplementaryDocument(String countryCode, String documentType) {
        super(DocScanConstants.ID_DOCUMENT);
        this.countryCode = countryCode;
        this.documentType = documentType;
    }

    public static SelectedSupplementaryDocument.Builder builder() {
        return new SelectedSupplementaryDocument.Builder();
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
         * Sets the country code of the {@link SelectedSupplementaryDocument}
         *
         * @param countryCode the country code
         * @return the builder
         */
        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * Sets the document type of the {@link SelectedSupplementaryDocument}
         *
         * @param documentType the document type
         * @return the builder
         */
        public Builder withDocumentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public SelectedSupplementaryDocument build() {
            return new SelectedSupplementaryDocument(countryCode, documentType);
        }

    }

}
