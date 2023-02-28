package com.yoti.api.client.identity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareSessionQrCode {

    @JsonProperty(Property.ID)
    private String id;

    @JsonProperty(Property.URI)
    private String uri;

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    private static final class Property {

        private static final String ID = "id";
        private static final String URI = "uri";

    }

}
