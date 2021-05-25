package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplementaryDocumentResourceResponse extends ResourceResponse {

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("issuing_country")
    private String issuingCountry;

    @JsonProperty("pages")
    private List<PageResponse> pages;

    @JsonProperty("document_fields")
    private DocumentFieldsResponse documentFields;

    @JsonProperty("file")
    private FileResponse documentFile;

    public String getDocumentType() {
        return documentType;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public List<PageResponse> getPages() {
        return pages;
    }

    public DocumentFieldsResponse getDocumentFields() {
        return documentFields;
    }

    public List<? extends SupplementaryDocumentTextExtractionTaskResponse> getTextExtractionTasks() {
        return filterTasksByType(SupplementaryDocumentTextExtractionTaskResponse.class);
    }

    public FileResponse getDocumentFile() {
        return documentFile;
    }

}
