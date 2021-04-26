package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.retrieve.sources.SearchProfileSourcesResponse;
import com.yoti.api.client.docs.session.retrieve.sources.TypeListSourcesResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SearchProfileSourcesResponse.class, name = DocScanConstants.PROFILE),
        @JsonSubTypes.Type(value = TypeListSourcesResponse.class, name = DocScanConstants.TYPE_LIST),
})
public abstract class CaSourcesResponse {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }
}
