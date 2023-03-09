package com.yoti.api.client.identity.policy;

import java.util.Objects;

import com.yoti.validation.Validation;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WantedAnchor that = (WantedAnchor) o;
        return Objects.equals(value, that.value) && Objects.equals(subType, that.subType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, subType);
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
            Validation.notNullOrEmpty(value, Property.NAME);
            Validation.notNull(subType, Property.SUB_TYPE);

            return new WantedAnchor(this);
        }

    }

    private static final class Property {

        private static final String NAME = "name";
        private static final String SUB_TYPE = "sub_type";

    }

}
