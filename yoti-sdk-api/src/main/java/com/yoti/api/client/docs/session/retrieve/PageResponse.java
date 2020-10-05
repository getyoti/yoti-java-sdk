package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface PageResponse {

    String getCaptureMethod();

    MediaResponse getMedia();

    List<? extends FrameResponse> getFrames();

}
