package com.yoti.api.client.docs.session.retrieve.configuration.capture.source;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EndUserAllowedSourceResponse.class, name = DocScanConstants.END_USER),
        @JsonSubTypes.Type(value = RelyingBusinessAllowedSourceResponse.class, name = DocScanConstants.RELYING_BUSINESS),
        @JsonSubTypes.Type(value = IbvAllowedSourceResponse.class, name = DocScanConstants.IBV)
})
public abstract class AllowedSourceResponse {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

}
