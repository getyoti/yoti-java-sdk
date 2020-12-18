package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeRestriction {

    @JsonProperty("inclusion")
    private final String inclusion;

    @JsonProperty("document_types")
    private final List<String> documentTypes;

    TypeRestriction(String inclusion, List<String> documentTypes) {
        notNull(inclusion, "inclusion");
        notNull(documentTypes, "documentTypes");
        this.inclusion = inclusion;
        this.documentTypes = documentTypes;
    }

    public String getInclusion() {
        return inclusion;
    }

    public List<String> getDocumentTypes() {
        return documentTypes;
    }

}
