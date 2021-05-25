package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FrameResponse {

    @JsonProperty("media")
    private MediaResponse media;

    public MediaResponse getMedia() {
        return media;
    }

}
