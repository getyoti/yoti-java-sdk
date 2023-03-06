package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StaticLivenessResourceResponse extends LivenessResourceResponse {

    @JsonProperty("image")
    private ImageResponse image;

    public ImageResponse getImage() {
        return image;
    }

}
