package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.create.objective.Objective;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequiredSupplementaryDocument extends RequiredDocument {

    @JsonProperty("objective")
    private final Objective objective;

    @JsonProperty("document_types")
    private final List<String> documentTypes;

    @JsonProperty("country_codes")
    private final List<String> countryCodes;

    RequiredSupplementaryDocument(Objective objective, List<String> documentTypes, List<String> countryCodes) {
        this.objective = objective;
        this.documentTypes = documentTypes;
        this.countryCodes = countryCodes;
    }

    public static RequiredSupplementaryDocument.Builder builder() {
        return new RequiredSupplementaryDocument.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.SUPPLEMENTARY_DOCUMENT;
    }

    /**
     * Get the objective this required supplementary document
     * will satisfy
     *
     * @return the objective
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * Get the list of document types that can be used
     * to satisfy this required supplementary document
     *
     * @return the list of document types
     */
    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    /**
     * Get the list of country codes that can be used
     * to satisfy this required supplementary document
     *
     * @return the list of country codes
     */
    public List<String> getCountryCodes() {
        return countryCodes;
    }

    public static class Builder {

        private Objective objective;
        private List<String> documentTypes;
        private List<String> countryCodes;

        private Builder() {}

        public Builder withObjective(Objective objective) {
            this.objective = objective;
            return this;
        }

        public Builder withDocumentTypes(List<String> documentTypes) {
            notNull(documentTypes, "documentTypes");
            this.documentTypes = documentTypes;
            return this;
        }

        public Builder withCountryCodes(List<String> countryCodes) {
            notNull(countryCodes, "countryCodes");
            this.countryCodes = countryCodes;
            return this;
        }

        public RequiredSupplementaryDocument build() {
            notNull(objective, "objective");
            return new RequiredSupplementaryDocument(objective, documentTypes, countryCodes);
        }
    }

}
