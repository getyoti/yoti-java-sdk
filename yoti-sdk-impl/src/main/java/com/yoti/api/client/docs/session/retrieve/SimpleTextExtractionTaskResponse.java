package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleTextExtractionTaskResponse extends SimpleTaskResponse implements TextExtractionTaskResponse {

    @Override
    public List<GeneratedTextDataCheckResponse> getGeneratedTextDataChecks() {
        return filterGeneratedChecksByType(GeneratedTextDataCheckResponse.class);
    }

}
