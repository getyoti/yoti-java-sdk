package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredIdDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.source.AllowedSourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.source.RelyingBusinessAllowedSourceResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequiredLivenessResourceResponse.class, name = DocScanConstants.LIVENESS),
        @JsonSubTypes.Type(value = RequiredIdDocumentResourceResponse.class, name = DocScanConstants.ID_DOCUMENT),
})
public abstract class RequiredResourceResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private String id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("allowed_sources")
    private List<AllowedSourceResponse> allowedSources;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public List<AllowedSourceResponse> getAllowedSources() {
        return allowedSources;
    }

    /**
     * Returns {@code true} if the Relying Business is allowed to upload resources
     * to satisfy the requirement.
     *
     * @return if the end user is allowed to upload resources
     */
    public boolean isRelyingBusinessAllowed() {
        if (allowedSources == null) {
            return false;
        }

        return allowedSources.stream()
                .anyMatch(RelyingBusinessAllowedSourceResponse.class::isInstance);
    }

}
