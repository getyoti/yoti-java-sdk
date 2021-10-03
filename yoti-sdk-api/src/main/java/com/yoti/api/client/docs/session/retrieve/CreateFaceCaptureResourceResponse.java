package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFaceCaptureResourceResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("frames")
    private int frames;

    /**
     * Returns the ID of the newly created Face Capture resource
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the number of image frames required
     *
     * @return the number of image frames
     */
    public int getFrames() {
        return frames;
    }

}
