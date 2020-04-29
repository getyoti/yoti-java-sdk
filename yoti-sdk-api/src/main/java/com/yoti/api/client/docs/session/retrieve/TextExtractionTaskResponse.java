package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface TextExtractionTaskResponse extends TaskResponse {

    List<GeneratedTextDataCheckResponse> getGeneratedTextDataChecks();

}
