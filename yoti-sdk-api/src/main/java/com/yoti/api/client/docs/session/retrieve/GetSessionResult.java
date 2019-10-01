package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface GetSessionResult {

    long getClientSessionTokenTtl();

    String getSessionId();

    String getUserTrackingId();

    String getState();

    String getClientSessionToken();

    List<? extends CheckResponse> getChecks();

    ResourceContainer getResources();

}
