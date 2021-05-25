package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public class IdDocTextExtractionTaskResponse extends TaskResponse {

    public List<GeneratedTextDataCheckResponse> getGeneratedTextDataChecks() {
        return filterGeneratedChecksByType(GeneratedTextDataCheckResponse.class);
    }


}
