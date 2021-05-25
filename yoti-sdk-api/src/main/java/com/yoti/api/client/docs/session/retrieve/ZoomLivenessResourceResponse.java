package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZoomLivenessResourceResponse extends LivenessResourceResponse {

    @JsonProperty("facemap")
    private FaceMapResponse faceMap;

    @JsonProperty("frames")
    private List<FrameResponse> frames;

    public FaceMapResponse getFaceMap() {
        return faceMap;
    }

    public List<? extends FrameResponse> getFrames() {
        return frames;
    }

}
