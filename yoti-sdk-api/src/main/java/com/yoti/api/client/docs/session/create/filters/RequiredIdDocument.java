package com.yoti.api.client.docs.session.create.filters;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines an identity document to be provided by the user
 */
public class RequiredIdDocument extends RequiredDocument {

    @JsonProperty("filter")
    private final DocumentFilter filter;

    private RequiredIdDocument(DocumentFilter filter) {
        this.filter = filter;
    }

    public static RequiredIdDocument.Builder builder() {
        return new RequiredIdDocument.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.ID_DOCUMENT;
    }

    /**
     * Filter for the required document
     *
     * @return the filter
     */
    public DocumentFilter getFilter() {
        return filter;
    }

    public static class Builder {

        private DocumentFilter filter;

        public Builder withFilter(DocumentFilter filter) {
            this.filter = filter;
            return this;
        }

        public RequiredIdDocument build() {
            return new RequiredIdDocument(filter);
        }

    }

}
