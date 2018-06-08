package com.yoti.api.client.qrcode.policy;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see Attribute
 *
 */
public class SimpleAttribute implements Attribute {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("anchors")
    private final List<String> anchors;

    @JsonProperty("derivation")
    private final String derivation;

    @JsonProperty("optional")
    private final boolean optional;

    public SimpleAttribute(String name, List<String> anchors, String derivation, boolean optional) {
        this.name = name;
        this.anchors = anchors;
        this.derivation = derivation;
        this.optional = optional;
    }

    /**
     * @see Attribute#getName()
     *
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see Attribute#getAnchors()
     *
     */
    @Override
    public List<String> getAnchors() {
        return anchors;
    }

    /**
     * @see Attribute#getDerivation()
     *
     */
    @Override
    public String getDerivation() {
        return derivation;
    }

    /**
     * @see Attribute#isOptional()
     *
     */
    @Override
    public boolean isOptional() {
        return optional;
    }

}
