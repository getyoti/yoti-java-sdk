package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleDocumentFieldsResponse implements DocumentFieldsResponse {

    @JsonProperty("media")
    private SimpleMediaResponse media;

    public MediaResponse getMedia() {
        return media;
    }

}
