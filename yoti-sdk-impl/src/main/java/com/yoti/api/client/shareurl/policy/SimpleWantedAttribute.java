package com.yoti.api.client.shareurl.policy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see WantedAttribute
 *
 */
class SimpleWantedAttribute implements WantedAttribute {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("derivation")
    private final String derivation;

    @JsonProperty("optional")
    private final boolean optional;

    SimpleWantedAttribute(String name, String derivation, boolean optional) {
        this.name = name;
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
