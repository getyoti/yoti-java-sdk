package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface TaskResponse {

    String getId();

    String getState();

    String getCreated();

    String getLastUpdated();

    List<? extends GeneratedCheckResponse> getGeneratedChecks();

    List<? extends GeneratedMedia> getGeneratedMedia();

}
