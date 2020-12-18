package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse {

    @JsonProperty("capture_method")
    private String captureMethod;

    @JsonProperty("media")
    private MediaResponse media;

    @JsonProperty("frames")
    private List<FrameResponse> frames;

    public String getCaptureMethod() {
        return captureMethod;
    }

    public MediaResponse getMedia() {
        return media;
    }

    public List<? extends FrameResponse> getFrames() {
        return frames;
    }

}
