package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "liveness_type", defaultImpl = LivenessResourceResponse.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ZoomLivenessResourceResponse.class, name = DocScanConstants.ZOOM),
})
public class LivenessResourceResponse extends ResourceResponse {

    @JsonProperty("liveness_type")
    private String livenessType;

    public String getLivenessType() {
        return livenessType;
    }

}
