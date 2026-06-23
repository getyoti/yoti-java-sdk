package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

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

    @JsonProperty("allow_expired_documents")
    private final Boolean allowExpiredDocuments;

    @JsonProperty("allow_digital_ids")
    private final Boolean allowDigitalIds;

    @JsonProperty("allowed_providers")
    private final List<AllowedProviderPayload> allowedProviders;

    DocumentFilter(String type, Boolean allowNonLatinDocuments, Boolean allowExpiredDocuments,
            Boolean allowDigitalIds, List<AllowedProviderPayload> allowedProviders) {
        this.type = type;
        this.allowNonLatinDocuments = allowNonLatinDocuments;
        this.allowExpiredDocuments = allowExpiredDocuments;
        this.allowDigitalIds = allowDigitalIds;
        this.allowedProviders = allowedProviders;
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

    /**
     * Returns if the {@link DocumentFilter} should allow expired documents
     *
     * @return boolean flag
     */
    public Boolean getAllowExpiredDocuments() {
        return allowExpiredDocuments;
    }

    /**
     * Whether to allow digital IDs to satisfy the filter
     *
     * @return boolean flag
     */
    public Boolean getAllowDigitalIds() {
        return allowDigitalIds;
    }

    /**
     * The list of digital ID providers that are allowed to satisfy the filter
     *
     * @return the allowed providers
     */
    public List<AllowedProviderPayload> getAllowedProviders() {
        return allowedProviders;
    }

}
