package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.Media;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdPhotoResponse {

    @JsonProperty("media")
    private Media media;

    public Media getMedia() {
        return media;
    }
    
}
