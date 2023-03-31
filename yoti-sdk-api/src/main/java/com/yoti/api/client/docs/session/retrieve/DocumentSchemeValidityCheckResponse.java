package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentSchemeValidityCheckResponse extends CheckResponse {

    @JsonProperty("scheme")
    private String scheme;

    public String getScheme() {
        return scheme;
    }
}
