package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleZoomLivenessResourceResponse extends SimpleLivenessResourceResponse implements ZoomLivenessResourceResponse {

    @JsonProperty("facemap")
    private SimpleFaceMapResponse faceMap;

    @JsonProperty("frames")
    private List<SimpleFrameResponse> frames;

    @Override
    public FaceMapResponse getFaceMap() {
        return faceMap;
    }

    @Override
    public List<? extends FrameResponse> getFrames() {
        return frames;
    }

}
