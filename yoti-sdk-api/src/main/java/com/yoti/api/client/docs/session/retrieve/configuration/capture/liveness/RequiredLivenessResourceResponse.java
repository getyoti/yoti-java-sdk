package com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.RequiredResourceResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "liveness_type", visible = true, defaultImpl = UnknownRequiredLivenessResourceResponse.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequiredZoomLivenessResourceResponse.class, name = DocScanConstants.ZOOM),
        @JsonSubTypes.Type(value = RequiredStaticLivenessResourceResponse.class, name = DocScanConstants.STATIC)
})
public abstract class RequiredLivenessResourceResponse extends RequiredResourceResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("liveness_type")
    private String livenessType;

    @Override
    public String getType() {
        return type;
    }

    public String getLivenessType() {
        return livenessType;
    }

}
