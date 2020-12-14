package com.yoti.api.client.shareurl.policy;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WantedAnchor {

    @JsonProperty("name")
    private final String value;

    @JsonProperty("sub_type")
    private final String subType;

    WantedAnchor(String value, String subType) {
        notNullOrEmpty(value, "value");
        notNull(subType, "subType");

        this.value = value;
        this.subType = subType;
    }

    public static WantedAnchor.Builder builder() {
        return new WantedAnchor.Builder();
    }

    public String getValue() {
        return value;
    }

    public String getSubType() {
        return subType;
    }

    public static class Builder {

        private String value;
        private String subType;

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withSubType(String subType) {
            this.subType = subType;
            return this;
        }

        public WantedAnchor build() {
            return new WantedAnchor(value, subType);
        }

    }

}
