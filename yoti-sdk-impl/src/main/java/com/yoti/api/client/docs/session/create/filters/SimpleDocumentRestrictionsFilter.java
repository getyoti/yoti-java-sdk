package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.docs.DocScanConstants;

import java.util.List;

class SimpleDocumentRestrictionsFilter implements DocumentRestrictionsFilter {

    @JsonProperty("inclusion")
    private String inclusion;

    @JsonProperty("documents")
    private List<DocumentRestriction> documents;

    SimpleDocumentRestrictionsFilter(String inclusion, List<DocumentRestriction> documents) {
        this.inclusion = inclusion;
        this.documents = documents;
    }

    @Override
    public String getInclusion() {
        return inclusion;
    }

    @Override
    public List<DocumentRestriction> getDocuments() {
        return documents;
    }

    @Override
    public String getType() {
        return DocScanConstants.DOCUMENT_RESTRICTIONS;
    }

}
