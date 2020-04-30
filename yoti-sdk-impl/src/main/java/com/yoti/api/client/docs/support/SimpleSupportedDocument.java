package com.yoti.api.client.docs.support;

import com.fasterxml.jackson.annotation.JsonProperty;

class SimpleSupportedDocument implements SupportedDocument {

    @JsonProperty("type")
    private String type;

    @Override
    public String getType() {
        return type;
    }

}
