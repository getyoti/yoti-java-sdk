package com.yoti.api.client.docs.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedDocument {

    @JsonProperty("type")
    private String type;

    @JsonProperty("is_strictly_latin")
    private Boolean isStrictlyLatin;

    public String getType() {
        return type;
    }

    public Boolean getStrictlyLatin() {
        return isStrictlyLatin;
    }

}
