package com.yoti.api.client.docs.session.create.identityprofile.advanced;

import com.yoti.api.client.docs.session.create.filters.DocumentFilter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileSchemeConfigPayload {

    @JsonProperty("filter")
    private final DocumentFilter filter;

    private AdvancedIdentityProfileSchemeConfigPayload(DocumentFilter filter) {
        this.filter = filter;
    }

    public static AdvancedIdentityProfileSchemeConfigPayload.Builder builder() {
        return new AdvancedIdentityProfileSchemeConfigPayload.Builder();
    }

    /**
     * Gets the filter for the configuration
     *
     * @return the filter
     */
    public DocumentFilter getFilter() {
        return filter;
    }

    public static final class Builder {

        private DocumentFilter filter;

        private Builder() {}

        /**
         * Sets the filter for the scheme configuration
         *
         * @param filter the document filter
         * @return the builder
         */
        public Builder withFilter(DocumentFilter filter) {
            this.filter = filter;
            return this;
        }

        public AdvancedIdentityProfileSchemeConfigPayload build() {
            return new AdvancedIdentityProfileSchemeConfigPayload(filter);
        }

    }


}
