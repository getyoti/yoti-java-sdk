package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedCountryResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("supported_documents")
    private List<SupportedDocumentResponse> supportedDocuments;

    /**
     * Returns the ISO Country Code of the supported country
     *
     * @return the country code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns a list of document types that are supported for the country code
     *
     * @return the supported documents
     */
    public List<SupportedDocumentResponse> getSupportedDocuments() {
        return supportedDocuments;
    }

}
