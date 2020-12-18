package com.yoti.api.client.docs.support;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedCountry {

    @JsonProperty("code")
    private String code;

    @JsonProperty("supported_documents")
    private List<SupportedDocument> supportedDocuments;

    public String getCode() {
        return code;
    }

    public List<? extends SupportedDocument> getSupportedDocuments() {
        return supportedDocuments;
    }

}
