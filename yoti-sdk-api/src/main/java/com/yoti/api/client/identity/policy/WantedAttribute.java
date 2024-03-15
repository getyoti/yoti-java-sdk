package com.yoti.api.client.identity.policy;

import static com.yoti.validation.Validation.notNullOrEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.identity.constraint.Constraint;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WantedAttribute {

    @JsonProperty(Property.NAME)
    private final String name;

    @JsonProperty(Property.DERIVATION)
    private final String derivation;

    @JsonProperty(Property.OPTIONAL)
    private final boolean optional;

    @JsonProperty(Property.ACCEPT_SELF_ASSERTED)
    private final Boolean acceptSelfAsserted;

    @JsonProperty(Property.CONSTRAINTS)
    private final List<Constraint> constraints;

    private WantedAttribute(Builder builder) {
        name = builder.name;
        derivation = builder.derivation;
        optional = builder.optional;
        acceptSelfAsserted = builder.acceptSelfAsserted;
        constraints = builder.constraints;
    }

    public String getName() {
        return name;
    }

    public String getDerivation() {
        return derivation;
    }

    public boolean isOptional() {
        return optional;
    }

    public Boolean getAcceptSelfAsserted() {
        return acceptSelfAsserted;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public boolean hasConstraints() {
        return !constraints.isEmpty();
    }

    public static Builder builder() {
        return new Builder();
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
            Validation.notNullOrEmpty(name, Property.NAME);

            return new WantedAttribute(this);
        }

    }

    private static final class Property {

        private static final String NAME = "name";
        private static final String DERIVATION = "derivation";
        private static final String OPTIONAL = "optional";
        private static final String ACCEPT_SELF_ASSERTED = "accept_self_asserted";
        private static final String CONSTRAINTS = "constraints";

    }

}
