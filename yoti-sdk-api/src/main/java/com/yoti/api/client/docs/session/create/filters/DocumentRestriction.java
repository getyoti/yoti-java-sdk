package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentRestriction {

    @JsonProperty("country_codes")
    private final List<String> countryCodes;

    @JsonProperty("document_types")
    private final List<String> documentTypes;

    DocumentRestriction(List<String> countryCodes, List<String> documentTypes) {
        this.countryCodes = countryCodes;
        this.documentTypes = documentTypes;
    }

    public static DocumentRestriction.Builder builder() {
        return new DocumentRestriction.Builder();
    }

    public List<String> getCountryCodes() {
        return countryCodes;
    }

    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    public static class Builder {

        private List<String> countryCodes;
        private List<String> documentTypes;

        public Builder withCountries(List<String> countryCodes) {
            this.countryCodes = countryCodes;
            return this;
        }

        public Builder withDocumentTypes(List<String> documentTypes) {
            this.documentTypes = documentTypes;
            return this;
        }

        public DocumentRestriction build() {
            return new DocumentRestriction(countryCodes, documentTypes);
        }

    }

}
