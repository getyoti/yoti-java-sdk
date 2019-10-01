package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface CheckResponse {

    String getId();

    String getState();

    List<String> getResourcesUsed();

    List<? extends GeneratedMedia> getGeneratedMedia();

    ReportResponse getReport();

    String getCreated();

    String getLastUpdated();

}
