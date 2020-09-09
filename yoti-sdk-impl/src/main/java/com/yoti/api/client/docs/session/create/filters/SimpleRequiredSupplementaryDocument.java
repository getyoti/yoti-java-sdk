package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.create.objective.Objective;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleRequiredSupplementaryDocument extends SimpleRequiredDocument implements RequiredSupplementaryDocument {

    @JsonProperty("objective")
    private final Objective objective;

    @JsonProperty("document_types")
    private final List<String> documentTypes;

    @JsonProperty("country_codes")
    private final List<String> countryCodes;

    SimpleRequiredSupplementaryDocument(Objective objective, List<String> documentTypes, List<String> countryCodes) {
        this.objective = objective;
        this.documentTypes = documentTypes;
        this.countryCodes = countryCodes;
    }

    @Override
    public String getType() {
        return DocScanConstants.SUPPLEMENTARY_DOCUMENT;
    }

    @Override
    public Objective getObjective() {
        return objective;
    }

    @Override
    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    @Override
    public List<String> getCountryCodes() {
        return countryCodes;
    }
}
