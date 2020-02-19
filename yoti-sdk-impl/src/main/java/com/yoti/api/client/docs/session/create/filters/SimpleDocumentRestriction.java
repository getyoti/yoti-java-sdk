package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
class SimpleDocumentRestriction implements DocumentRestriction {

    @JsonProperty("country_codes")
    private List<String> countryCodes;

    @JsonProperty("document_types")
    private List<String> documentTypes;

    SimpleDocumentRestriction(List<String> countryCodes, List<String> documentTypes) {
        this.countryCodes = countryCodes;
        this.documentTypes = documentTypes;
    }

    @Override
    public List<String> getCountryCodes() {
        return countryCodes;
    }

    @Override
    public List<String> getDocumentTypes() {
        return documentTypes;
    }

}
