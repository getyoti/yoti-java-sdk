package com.yoti.api.client.docs.session.retrieve.identityprofile;

import com.yoti.api.client.docs.session.retrieve.MediaResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfilePreviewResponse {

    @JsonProperty("media")
    private MediaResponse media;

    public MediaResponse getMedia() {
        return media;
    }

}
