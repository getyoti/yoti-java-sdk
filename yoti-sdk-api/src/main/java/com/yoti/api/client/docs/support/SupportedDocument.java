package com.yoti.api.client.docs.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedDocument {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

}
