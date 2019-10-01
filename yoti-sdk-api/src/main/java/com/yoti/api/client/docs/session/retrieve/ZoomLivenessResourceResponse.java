package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface ZoomLivenessResourceResponse extends LivenessResourceResponse {

    FaceMapResponse getFaceMap();

    List<? extends FrameResponse> getFrames();

}
