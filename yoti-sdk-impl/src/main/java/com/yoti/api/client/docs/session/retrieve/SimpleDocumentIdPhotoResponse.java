package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleDocumentIdPhotoResponse implements DocumentIdPhotoResponse {

    @JsonProperty("media")
    private SimpleMediaResponse media;

    @Override
    public MediaResponse getMedia() {
        return media;
    }
}
