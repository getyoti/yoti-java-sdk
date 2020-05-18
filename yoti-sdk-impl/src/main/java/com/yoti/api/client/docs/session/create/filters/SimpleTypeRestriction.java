package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class SimpleTypeRestriction implements TypeRestriction {

    @JsonProperty("inclusion")
    private String inclusion;

    @JsonProperty("document_types")
    private List<String> documentTypes;

    SimpleTypeRestriction(String inclusion, List<String> documentTypes) {
        notNull(inclusion, "inclusion");
        notNull(documentTypes, "documentTypes");
        this.inclusion = inclusion;
        this.documentTypes = documentTypes;
    }

    @Override
    public String getInclusion() {
        return inclusion;
    }

    @Override
    public List<String> getDocumentTypes() {
        return documentTypes;
    }

}
