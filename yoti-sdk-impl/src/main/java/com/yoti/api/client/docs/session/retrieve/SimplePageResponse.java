package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePageResponse implements PageResponse {

    @JsonProperty("capture_method")
    private String captureMethod;

    @JsonProperty("media")
    private SimpleMediaResponse media;

    @Override
    public String getCaptureMethod() {
        return captureMethod;
    }

    @Override
    public MediaResponse getMedia() {
        return media;
    }

}
