package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentIdPhotoResponse {

    @JsonProperty("media")
    private MediaResponse media;

    /**
     * Retrieve the {@link MediaResponse} related to the
     * document ID photo
     *
     * @return the media
     */
    public MediaResponse getMedia() {
        return media;
    }

}
