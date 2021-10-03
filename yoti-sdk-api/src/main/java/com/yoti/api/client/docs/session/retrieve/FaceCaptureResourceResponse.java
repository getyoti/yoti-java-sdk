package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceCaptureResourceResponse extends ResourceResponse {

    @JsonProperty("image")
    private FaceCaptureImageResponse image;

    public FaceCaptureImageResponse getImage() {
        return image;
    }
    
}
