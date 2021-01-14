package com.yoti.api.client.sandbox.profile.request.attribute;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxAttribute {

    private final String name;
    private final String value;
    private final String derivation;
    private final List<SandboxAnchor> anchors;

    private SandboxAttribute(String name,
            String value,
            String derivation,
            List<SandboxAnchor> anchors) {
        this.name = name;
        this.value = value;
        this.derivation = derivation;
        this.anchors = anchors == null ? Collections.emptyList() : anchors;
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

    @JsonProperty("anchors")
    public List<SandboxAnchor> getAnchors() {
        return anchors;
    }

    public static SandboxAttributeBuilder builder() {
        return new SandboxAttributeBuilder();
    }

    public static class SandboxAttributeBuilder {

        private String name;
        private String value;
        private String derivation;
        private List<SandboxAnchor> anchors;

        private SandboxAttributeBuilder() {
        }

        public SandboxAttributeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SandboxAttributeBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public SandboxAttributeBuilder withDerivation(String derivation) {
            this.derivation = derivation;
            return this;
        }

        public SandboxAttributeBuilder withAnchors(List<SandboxAnchor> anchors) {
            this.anchors = anchors;
            return this;
        }

        public SandboxAttribute build() {
            return new SandboxAttribute(name, value, derivation, anchors);
        }

    }

}
