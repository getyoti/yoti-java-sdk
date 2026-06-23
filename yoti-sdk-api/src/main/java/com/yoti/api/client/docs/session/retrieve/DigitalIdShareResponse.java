package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a digital ID share that was performed during a session. A share that
 * was not completed successfully will have an {@link #getError() error}.
 */
public class DigitalIdShareResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("issuing_country")
    private String issuingCountry;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("resource_id")
    private String resourceId;

    @JsonProperty("error")
    private DigitalIdShareErrorResponse error;

    public String getId() {
        return id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public String getProvider() {
        return provider;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * The id of the resource linked to the share
     *
     * @return the resource id
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * The error that occurred if the share was not completed successfully, otherwise {@code null}
     *
     * @return the error, may be {@code null}
     */
    public DigitalIdShareErrorResponse getError() {
        return error;
    }

}
