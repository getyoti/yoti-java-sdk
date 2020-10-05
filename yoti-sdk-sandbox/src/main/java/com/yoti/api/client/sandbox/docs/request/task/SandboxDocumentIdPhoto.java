package com.yoti.api.client.sandbox.docs.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxDocumentIdPhoto {

    @JsonProperty("content_type")
    private final String contentType;

    @JsonProperty("data")
    private final String data;

    SandboxDocumentIdPhoto(String contentType, String data) {
        this.contentType = contentType;
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public String getData() {
        return data;
    }
}
