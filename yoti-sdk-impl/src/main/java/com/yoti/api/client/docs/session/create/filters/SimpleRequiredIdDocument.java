package com.yoti.api.client.docs.session.create.filters;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleRequiredIdDocument extends SimpleRequiredDocument implements RequiredIdDocument {

    private DocumentFilter filter;

    public SimpleRequiredIdDocument(DocumentFilter filter) {
        this.filter = filter;
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT;
    }

    @Override
    @JsonProperty("filter")
    public DocumentFilter getFilter() {
        return filter;
    }

}
