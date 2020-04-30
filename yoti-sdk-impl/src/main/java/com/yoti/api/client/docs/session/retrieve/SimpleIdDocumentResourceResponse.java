package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleIdDocumentResourceResponse extends SimpleResourceResponse implements IdDocumentResourceResponse {

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("issuing_country")
    private String issuingCountry;

    @JsonProperty("pages")
    private List<SimplePageResponse> pages;

    @JsonProperty("document_fields")
    private SimpleDocumentFieldsResponse documentFields;

    public String getDocumentType() {
        return documentType;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public List<? extends PageResponse> getPages() {
        return pages;
    }

    public DocumentFieldsResponse getDocumentFields() {
        return documentFields;
    }

    @Override
    public List<TextExtractionTaskResponse> getTextExtractionTasks() {
        return filterTasksByType(TextExtractionTaskResponse.class);
    }

}
