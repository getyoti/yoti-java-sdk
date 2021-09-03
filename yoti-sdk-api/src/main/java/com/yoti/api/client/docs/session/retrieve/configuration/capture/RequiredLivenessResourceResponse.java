package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "liveness_type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequiredZoomLivenessResourceResponse.class, name = DocScanConstants.ZOOM),
        @JsonSubTypes.Type(value = UnknownRequiredLivenessResourceResponse.class, name = "UNKNOWN")
})
public abstract class RequiredLivenessResourceResponse extends RequiredResourceResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("liveness_type")
    private String livenessType;

}
