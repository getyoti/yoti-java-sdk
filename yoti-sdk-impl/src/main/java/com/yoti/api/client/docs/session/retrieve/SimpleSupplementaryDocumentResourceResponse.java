package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleSupplementaryDocumentResourceResponse extends SimpleResourceResponse implements SupplementaryDocumentResourceResponse {

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("issuing_country")
    private String issuingCountry;

    @JsonProperty("pages")
    private List<SimplePageResponse> pages;

    @JsonProperty("document_fields")
    private SimpleDocumentFieldsResponse documentFields;

    @JsonProperty("file")
    private SimpleFileResponse documentFile;

    @Override
    public String getDocumentType() {
        return documentType;
    }

    @Override
    public String getIssuingCountry() {
        return issuingCountry;
    }

    @Override
    public List<SimplePageResponse> getPages() {
        return pages;
    }

    @Override
    public SimpleDocumentFieldsResponse getDocumentFields() {
        return documentFields;
    }

    @Override
    public List<? extends SupplementaryDocumentTextExtractionTaskResponse> getTextExtractionTasks() {
        return filterTasksByType(SupplementaryDocumentTextExtractionTaskResponse.class);
    }

    @Override
    public FileResponse getDocumentFile() {
        return documentFile;
    }
}
