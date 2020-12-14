package com.yoti.api.client.shareurl.policy;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.shareurl.constraint.Constraint;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Type and content of an user detail
 */
public class WantedAttribute {

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

    WantedAttribute(String name, String derivation, boolean optional, Boolean acceptSelfAsserted, List<Constraint> constraints) {
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

    public static WantedAttribute.Builder builder() {
        return new WantedAttribute.Builder();
    }

    /**
     * Name identifying the {@link WantedAttribute}
     *
     * @return name of the attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Additional derived criteria
     *
     * @return derivations
     */
    public String getDerivation() {
        return derivation;
    }

    /**
     * Defines the {@link WantedAttribute} as not mandatory
     *
     * @return optional
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Allows self asserted attributes
     * @return accept self asserted
     */
    public Boolean getAcceptSelfAsserted() {
        return acceptSelfAsserted;
    }

    /**
     * List of {@link Constraint} for a {@link WantedAttribute}
     *
     * @return the list of constrains
     */
    public List<Constraint> getConstraints() {
        return constraints;
    }

    public static class Builder {

        private String name;
        private String derivation;
        private boolean optional;
        private Boolean acceptSelfAsserted;
        private List<Constraint> constraints;

        private Builder() {
            this.constraints = new ArrayList<>();
        }
        
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder withDerivation(String derivation) {
            this.derivation = derivation;
            return this;
        }
        
        public Builder withOptional(boolean optional) {
            this.optional = optional;
            return this;
        }

        public Builder withAcceptSelfAsserted(boolean acceptSelfAsserted) {
            this.acceptSelfAsserted = acceptSelfAsserted;
            return this;
        }

        public Builder withConstraints(List<Constraint> constraints) {
            this.constraints = Collections.unmodifiableList(constraints);
            return this;
        }

        public Builder withConstraint(Constraint constraint) {
            this.constraints.add(constraint);
            return this;
        }

        public WantedAttribute build() {
            return new WantedAttribute(name, derivation, optional, acceptSelfAsserted, constraints);
        }

    }

}
