package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequiredSupplementaryDocumentResource {

    @JsonProperty("document_types")
    private List<String> documentTypes;

    @JsonProperty("country_codes")
    private List<String> countryCodes;

    @JsonProperty("objective")
    private ObjectiveResponse objective;

    /**
     * Returns a list of document types that can be used to satisfy the requirement
     *
     * @return the document types
     */
    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    /**
     * Returns a list of country codes that can be used to satisfy the requirement
     *
     * @return the country codes
     */
    public List<String> getCountryCodes() {
        return countryCodes;
    }

    /**
     * Returns the objective that the {@link RequiredSupplementaryDocumentResource} will satisfy
     *
     * @return the objective
     */
    public ObjectiveResponse getObjective() {
        return objective;
    }

}
