package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StaticLivenessResourceResponse extends LivenessResourceResponse {

    @JsonProperty("image")
    private ImageResponse image;

    @JsonProperty("capture_type")
    private String captureType;

    public ImageResponse getImage() {
        return image;
    }

    public String getCaptureType() {
        return captureType;
    }

}
