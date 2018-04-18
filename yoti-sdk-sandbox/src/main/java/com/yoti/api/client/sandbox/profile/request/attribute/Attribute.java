package com.yoti.api.client.sandbox.profile.request.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribute {
    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;
    @JsonProperty("derivation")
    private String derivation;
    @JsonProperty("optional")
    private String optional;

    @JsonCreator
    private Attribute(@JsonProperty("name") String name,
            @JsonProperty("value") String value,
            @JsonProperty("derivation") String derivation,
            @JsonProperty("optional") String optional) {
        this.name = name;
        this.value = value;
        this.derivation = derivation;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDerivation() {
        return derivation;
    }

    public String getOptional() {
        return optional;
    }

    public static class AttributeBuilder {

        private String name;

        private String value;

        private String derivation = "";

        private String optional = "false";

        private AttributeBuilder() {
        }

        public static AttributeBuilder builder() {
            return new AttributeBuilder();
        }

        public AttributeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AttributeBuilder value(String value) {
            this.value = value;
            return this;
        }

        public AttributeBuilder derivation(String derivation) {
            this.derivation = derivation;
            return this;
        }

        public AttributeBuilder optional(String optional) {
            this.optional = optional;
            return this;
        }

        public Attribute build() {
            return new Attribute(name, value, derivation, optional);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Attribute attribute = (Attribute) o;

        return name.equals(attribute.name) && value.equals(attribute.value) && (
                derivation != null ? derivation.equals(attribute.derivation) : attribute.derivation == null) && (
                optional != null ? optional.equals(attribute.optional) : attribute.optional == null);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + (derivation != null ? derivation.hashCode() : 0);
        result = 31 * result + (optional != null ? optional.hashCode() : 0);
        return result;
    }
}
