package com.yoti.api.client.sandbox.docs.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxDocumentFilter {

    @JsonProperty("document_types")
    private final List<String> documentTypes;

    @JsonProperty("country_codes")
    private final List<String> countryCodes;

    SandboxDocumentFilter(List<String> documentTypes, List<String> countryCodes) {
        this.documentTypes = documentTypes;
        this.countryCodes = countryCodes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    public List<String> getCountryCodes() {
        return countryCodes;
    }

    /**
     * Builder for {@link SandboxDocumentFilter}
     */
    public static class Builder {

        private List<String> documentTypes = new ArrayList<>();
        private List<String> countryCodes = new ArrayList<>();

        public Builder withDocumentType(String documentType) {
            this.documentTypes.add(documentType);
            return this;
        }

        public Builder withDocumentTypes(List<String> documentTypes) {
            this.documentTypes = documentTypes;
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            this.countryCodes.add(countryCode);
            return this;
        }

        public Builder withCountryCodes(List<String> countryCodes) {
            this.countryCodes = countryCodes;
            return this;
        }

        public SandboxDocumentFilter build() {
            return new SandboxDocumentFilter(documentTypes, countryCodes);
        }

    }
}
