package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdDocTextExtractionTaskResponse extends TaskResponse {

    public List<GeneratedTextDataCheckResponse> getGeneratedTextDataChecks() {
        return filterGeneratedChecksByType(GeneratedTextDataCheckResponse.class);
    }


}
