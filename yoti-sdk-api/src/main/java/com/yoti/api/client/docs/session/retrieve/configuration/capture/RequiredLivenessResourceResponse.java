package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "liveness_type", visible = true, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequiredZoomLivenessResourceResponse.class, name = DocScanConstants.ZOOM),
})
public class RequiredLivenessResourceResponse extends RequiredResourceResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("liveness_type")
    private String livenessType;

    public String getType() {
        return type;
    }

    /**
     * Returns the liveness type that is required to satisfy the requirement
     *
     * @return the liveness type
     */
    public String getLivenessType() {
        return livenessType;
    }

}
