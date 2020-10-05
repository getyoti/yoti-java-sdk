package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePageResponse implements PageResponse {

    @JsonProperty("capture_method")
    private String captureMethod;

    @JsonProperty("media")
    private SimpleMediaResponse media;

    @JsonProperty("frames")
    private List<SimpleFrameResponse> frames;

    @Override
    public String getCaptureMethod() {
        return captureMethod;
    }

    @Override
    public MediaResponse getMedia() {
        return media;
    }

    @Override
    public List<? extends FrameResponse> getFrames() {
        return frames;
    }

}
