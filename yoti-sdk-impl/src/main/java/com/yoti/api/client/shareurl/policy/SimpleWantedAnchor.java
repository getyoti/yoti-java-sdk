package com.yoti.api.client.shareurl.policy;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

public class SimpleWantedAnchor implements WantedAnchor {

    @JsonProperty("name")
    private String value;

    @JsonProperty("sub_type")
    private String subType;

    SimpleWantedAnchor(String value, String subType) {
        notNullOrEmpty(value, "value");
        notNull(subType, "subType");

        this.value = value;
        this.subType = subType;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getSubType() {
        return subType;
    }
}
