package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdDocumentResourceResponse extends ResourceResponse {

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("issuing_country")
    private String issuingCountry;

    @JsonProperty("pages")
    private List<PageResponse> pages;

    @JsonProperty("document_fields")
    private DocumentFieldsResponse documentFields;

    @JsonProperty("document_id_photo")
    private DocumentIdPhotoResponse documentIdPhoto;

    @JsonProperty("expanded_document_fields")
    private ExpandedDocumentFieldsResponse expandedDocumentFields;

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

    public List<IdDocTextExtractionTaskResponse> getTextExtractionTasks() {
        return filterTasksByType(IdDocTextExtractionTaskResponse.class);
    }

    public DocumentIdPhotoResponse getDocumentIdPhoto() {
        return documentIdPhoto;
    }

    public ExpandedDocumentFieldsResponse getExpandedDocumentFields() {
        return expandedDocumentFields;
    }

}
