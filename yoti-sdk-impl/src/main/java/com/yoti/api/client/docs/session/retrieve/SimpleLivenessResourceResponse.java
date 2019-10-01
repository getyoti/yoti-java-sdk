package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "liveness_type", defaultImpl = SimpleLivenessResourceResponse.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleZoomLivenessResourceResponse.class, name = DocScanConstants.ZOOM),
})
public class SimpleLivenessResourceResponse extends SimpleResourceResponse implements LivenessResourceResponse {

}
