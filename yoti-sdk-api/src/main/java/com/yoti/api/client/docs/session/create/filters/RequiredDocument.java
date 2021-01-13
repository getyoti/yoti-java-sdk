package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines a document to be provided by the user
 */
public abstract class RequiredDocument {

    /**
     * The type of the required document
     *
     * @return the type
     */
    @JsonProperty("type")
    public abstract String getType();

    public static RequiredIdDocument.Builder forIdDocument() {
        return RequiredIdDocument.builder();
    }

    public static RequiredSupplementaryDocument.Builder forSupplementaryDocument() {
        return RequiredSupplementaryDocument.builder();
    }

}
