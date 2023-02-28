package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filter for a required document, allowing specification
 * of restrictive parameters
 */
public abstract class DocumentFilter {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("allow_non_latin_documents")
    private final Boolean allowNonLatinDocuments;

    DocumentFilter(String type, Boolean allowNonLatinDocuments) {
        this.type = type;
        this.allowNonLatinDocuments = allowNonLatinDocuments;
    }

    /**
     * The type of the filter
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Whether allow non latin documents
     *
     * @return boolean flag
     */
    public Boolean getAllowNonLatinDocuments() {
        return allowNonLatinDocuments;
    }

}
