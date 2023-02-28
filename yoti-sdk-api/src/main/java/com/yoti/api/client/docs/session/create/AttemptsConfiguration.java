package com.yoti.api.client.docs.session.create;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AttemptsConfiguration {

    @JsonProperty("ID_DOCUMENT_TEXT_DATA_EXTRACTION")
    private final Map<String, Integer> idDocumentTextDataExtraction;

    private AttemptsConfiguration(Map<String, Integer> idDocumentTextDataExtraction) {
        this.idDocumentTextDataExtraction = idDocumentTextDataExtraction;
    }

    public static AttemptsConfiguration.Builder builder() {
        return new AttemptsConfiguration.Builder();
    }

    /**
     * Returns the attempts configuration {@link Map} for ID document text extraction task retries
     *
     * @return the attempts configuration map
     */
    public Map<String, Integer> getIdDocumentTextDataExtraction() {
        return idDocumentTextDataExtraction;
    }

    public static final class Builder {

        private static final String RECLASSIFICATION = "RECLASSIFICATION";
        private static final String GENERIC = "GENERIC";

        private Map<String, Integer> idDocumentTextDataExtractionAttempts;

        private Builder() {}

        private Builder withIdDocumentTextExtractionCategoryRetries(String category, int numberOfRetries) {
            if (idDocumentTextDataExtractionAttempts == null) {
                idDocumentTextDataExtractionAttempts = new HashMap<>();
            }

            idDocumentTextDataExtractionAttempts.put(category, numberOfRetries);
            return this;
        }

        /**
         * Sets the number of allowed RECLASSIFICATION attempts for a given ID document text extraction task
         *
         * @param numberOfRetries the number of allowed RECLASSIFICATION attempts
         * @return the builder
         */
        public Builder withIdDocumentTextExtractionReclassificationRetries(int numberOfRetries) {
            return withIdDocumentTextExtractionCategoryRetries(RECLASSIFICATION, numberOfRetries);
        }


        /**
         * Sets the number of allowed GENERIC attempts for a given ID document text extraction task
         *
         * @param numberOfRetries the number of allowed GENERIC attempts
         * @return the builder
         */
        public Builder withIdDocumentTextExtractionGenericRetries(int numberOfRetries) {
            return withIdDocumentTextExtractionCategoryRetries(GENERIC, numberOfRetries);
        }

        public AttemptsConfiguration build() {
            return new AttemptsConfiguration(idDocumentTextDataExtractionAttempts);
        }

    }

}
