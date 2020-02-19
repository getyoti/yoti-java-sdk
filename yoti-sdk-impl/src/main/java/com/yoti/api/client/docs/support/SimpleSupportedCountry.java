package com.yoti.api.client.docs.support;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class SimpleSupportedCountry implements SupportedCountry {

    @JsonProperty("code")
    private String code;

    @JsonProperty("supported_documents")
    private List<SimpleSupportedDocument> supportedDocuments;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public List<? extends SupportedDocument> getSupportedDocuments() {
        return supportedDocuments;
    }

}
