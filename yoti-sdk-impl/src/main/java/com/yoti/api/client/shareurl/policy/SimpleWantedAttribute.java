package com.yoti.api.client.shareurl.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.shareurl.constraint.Constraint;

import java.util.Collections;
import java.util.List;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

/**
 * @see WantedAttribute
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class SimpleWantedAttribute implements WantedAttribute {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("derivation")
    private final String derivation;

    @JsonProperty("optional")
    private final boolean optional;

    @JsonProperty("accept_self_asserted")
    private final Boolean acceptSelfAsserted;

    @JsonProperty("constraints")
    private final List<Constraint> constraints;

    SimpleWantedAttribute(String name, String derivation, boolean optional, Boolean acceptSelfAsserted, List<Constraint> constraints) {
        notNullOrEmpty(name, "name");

        this.name = name;
        this.derivation = derivation;
        this.optional = optional;
        this.acceptSelfAsserted = acceptSelfAsserted;

        if (constraints == null) {
            this.constraints = Collections.emptyList();
        } else {
            this.constraints = constraints;
        }
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
    public Boolean getAcceptSelfAsserted() {
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
