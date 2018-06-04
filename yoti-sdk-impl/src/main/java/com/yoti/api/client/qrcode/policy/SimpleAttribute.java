package com.yoti.api.client.qrcode.policy;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SimpleAttribute
 */
public class SimpleAttribute implements Attribute {

    /**
     * name
     */
    @JsonProperty("name")
    private final String name;

    /**
     * anchors
     */
    @JsonProperty("anchors")
    private final List<String> anchors;

    /**
     * derivation
     */
    @JsonProperty("derivation")
    private final String derivation;

    /**
     * optional
     */
    @JsonProperty("optional")
    private final boolean optional;

    public SimpleAttribute(String name, List<String> anchors, String derivation, boolean optional) {
        this.name = name;
        this.anchors = anchors;
        this.derivation = derivation;
        this.optional = optional;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getAnchors() {
        return anchors;
    }

    @Override
    public String getDerivation() {
        return derivation;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

}
