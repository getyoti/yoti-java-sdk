package com.yoti.api.client.qrcode.policy;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see WantedAttribute
 *
 */
public class SimpleWantedAttribute implements WantedAttribute {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("anchors")
    private final List<String> anchors;

    @JsonProperty("derivation")
    private final String derivation;

    @JsonProperty("optional")
    private final boolean optional;

    public SimpleWantedAttribute(String name, List<String> anchors, String derivation, boolean optional) {
        this.name = name;
        this.anchors = anchors;
        this.derivation = derivation;
        this.optional = optional;
    }

    /**
     * @see WantedAttribute#getName()
     *
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see WantedAttribute#getAnchors()
     *
     */
    @Override
    public List<String> getAnchors() {
        return anchors;
    }

    /**
     * @see WantedAttribute#getDerivation()
     *
     */
    @Override
    public String getDerivation() {
        return derivation;
    }

    /**
     * @see WantedAttribute#isOptional()
     *
     */
    @Override
    public boolean isOptional() {
        return optional;
    }

}
