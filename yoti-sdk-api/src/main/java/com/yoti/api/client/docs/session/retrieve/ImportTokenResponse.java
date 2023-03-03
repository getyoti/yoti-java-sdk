package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportTokenResponse {

    @JsonProperty(Property.MEDIA)
    private MediaResponse media;

    @JsonProperty(Property.FAILURE_REASON)
    private String failureReason;

    public MediaResponse getMedia() {
        return media;
    }

    public String getFailureReason() {
        return failureReason;
    }

    private static final class Property {

        private static final String MEDIA = "media";
        private static final String FAILURE_REASON = "failure_reason";

        private Property() { }

    }

}
