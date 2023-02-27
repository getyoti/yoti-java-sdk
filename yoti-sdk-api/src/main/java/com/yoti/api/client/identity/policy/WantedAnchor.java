package com.yoti.api.client.identity.policy;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WantedAnchor {

    @JsonProperty(Property.NAME)
    private final String value;

    @JsonProperty(Property.SUB_TYPE)
    private final String subType;

    private WantedAnchor(Builder builder) {
        value = builder.value;
        subType = builder.subType;
    }

    public String getValue() {
        return value;
    }

    public String getSubType() {
        return subType;
    }

    public static Builder builder() {
        return new Builder();
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
            notNullOrEmpty(value, Property.NAME);
            notNull(subType, Property.SUB_TYPE);

            return new WantedAnchor(this);
        }

    }

    private static final class Property {

        private static final String NAME = "name";
        private static final String SUB_TYPE = "sub_type";

    }

}
