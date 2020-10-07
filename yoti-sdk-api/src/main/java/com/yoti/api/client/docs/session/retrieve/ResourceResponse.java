package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface ResourceResponse {

    String getId();

    List<? extends TaskResponse> getTasks();
}
