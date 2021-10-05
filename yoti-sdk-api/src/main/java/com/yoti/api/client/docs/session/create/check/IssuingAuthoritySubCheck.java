package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.session.create.filters.DocumentFilter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssuingAuthoritySubCheck {

    @JsonProperty("requested")
    private final boolean requested;

    @JsonProperty("filter")
    private final DocumentFilter filter;

    private IssuingAuthoritySubCheck(boolean requested, DocumentFilter filter) {
        this.requested = requested;
        this.filter = filter;
    }

    public static IssuingAuthoritySubCheck.Builder builder() {
        return new IssuingAuthoritySubCheck.Builder();
    }

    /**
     * Returns if the issuing authority sub check has been requested
     *
     * @return if the sub check has been requested
     */
    public boolean isRequested() {
        return requested;
    }

    /**
     * Returns the {@link DocumentFilter} that will drive which
     * documents the sub check is performed on
     *
     * @return the {@link DocumentFilter}
     */
    public DocumentFilter getFilter() {
        return filter;
    }

    public static class Builder {

        private boolean requested;
        private DocumentFilter documentFilter;

        private Builder() { }

        /**
         * Sets whether the sub check will be requested for the Authenticity check
         *
         * @param requested if the sub check is requested
         * @return the builder
         */
        public Builder withRequested(boolean requested) {
            this.requested = requested;
            return this;
        }

        /**
         * Sets the {@link DocumentFilter} that will be used to determine
         * which documents the sub check is performed on.
         *
         * @param documentFilter the document filter
         * @return the builder
         */
        public Builder withDocumentFilter(DocumentFilter documentFilter) {
            this.documentFilter = documentFilter;
            return this;
        }

        public IssuingAuthoritySubCheck build() {
            return new IssuingAuthoritySubCheck(requested, documentFilter);
        }

    }

}
