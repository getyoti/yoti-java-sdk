package com.yoti.api.client.sandbox.profile.request.attribute;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxAttribute {

    private final String name;
    private final String value;
    private final String derivation;
    private final String optional;

    private SandboxAttribute(String name, String value, String derivation, String optional) {
        this.name = name;
        this.value = value;
        this.derivation = derivation;
        this.optional = optional;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("derivation")
    public String getDerivation() {
        return derivation;
    }

    @JsonProperty("optional")
    public String getOptional() {
        return optional;
    }

    public static SandboxAttributeBuilder builder() {
        return new SandboxAttributeBuilder();
    }

    public static class SandboxAttributeBuilder {

        private String name;
        private String value;
        private String derivation = "";
        private boolean optional = false;

        private SandboxAttributeBuilder() {
        }

        public SandboxAttributeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SandboxAttributeBuilder value(String value) {
            this.value = value;
            return this;
        }

        public SandboxAttributeBuilder derivation(String derivation) {
            this.derivation = derivation;
            return this;
        }

        public SandboxAttributeBuilder optional(boolean optional) {
            this.optional = optional;
            return this;
        }

        public SandboxAttribute build() {
            return new SandboxAttribute(name, value, derivation, Boolean.toString(optional));
        }

    }

}
