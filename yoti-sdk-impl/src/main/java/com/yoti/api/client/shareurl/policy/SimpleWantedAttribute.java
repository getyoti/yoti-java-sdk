package com.yoti.api.client.shareurl.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.shareurl.constraint.Constraint;

import java.util.Collections;
import java.util.List;

/**
 * @see WantedAttribute
 */
class SimpleWantedAttribute implements WantedAttribute {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("derivation")
    private final String derivation;

    @JsonProperty("optional")
    private final boolean optional;

    @JsonProperty("accept_self_asserted")
    private final boolean acceptSelfAsserted;

    @JsonProperty("constraints")
    private final List<Constraint> constraints;

    SimpleWantedAttribute(String name, String derivation, boolean optional, boolean acceptSelfAsserted, List<Constraint> constraints) {
        this.name = name;
        this.derivation = derivation;
        this.optional = optional;
        this.acceptSelfAsserted = acceptSelfAsserted;

        // TODO: Check with Mike if this is ok?
        this.constraints = constraints == null ? Collections.<Constraint>emptyList() : constraints;
    }

    /**
     * @see WantedAttribute#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see WantedAttribute#getDerivation()
     */
    @Override
    public String getDerivation() {
        return derivation;
    }

    /**
     * @see WantedAttribute#isOptional()
     */
    @Override
    public boolean isOptional() {
        return optional;
    }

    /**
     * @see WantedAttribute#getAcceptSelfAsserted()
     */
    @Override
    public boolean getAcceptSelfAsserted() {
        return acceptSelfAsserted;
    }

    /**
     * @see WantedAttribute#getConstraints()
     */
    @Override
    public List<Constraint> getConstraints() {
        return constraints;
    }

}
