package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportTokenResponse {

    @JsonProperty("media")
    private MediaResponse media;

    @JsonProperty("failure_reason")
    private String failureReason;

    public MediaResponse getMedia() {
        return media;
    }

    public String getFailureReason() {
        return failureReason;
    }

}
