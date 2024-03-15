package com.yoti.api.client.identity.policy;

import static com.yoti.validation.Validation.notNullOrEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonProperty(Property.ALTERNATIVE_NAMES)
    private final Set<String> alternativeNames;

    private WantedAttribute(Builder builder) {
        name = builder.name;
        derivation = builder.derivation;
        optional = builder.optional;
        acceptSelfAsserted = builder.acceptSelfAsserted;
        constraints = Collections.unmodifiableList(builder.constraints);
        alternativeNames = Collections.unmodifiableSet(builder.alternativeNames);
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

    public Set<String> getAlternativeNames() {
        return alternativeNames;
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
        private Set<String> alternativeNames;

        private Builder() {
            constraints = new ArrayList<>();
            alternativeNames = new HashSet<>();
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
            this.constraints.addAll(constraints);
            return this;
        }

        public Builder withConstraint(Constraint constraint) {
            constraints.add(constraint);
            return this;
        }

        public Builder withAlternativeNames(Set<String> alternativeNames) {
            this.alternativeNames.addAll(alternativeNames);
            return this;
        }

        public Builder withAlternativeName(String alternativeName) {
            alternativeNames.add(alternativeName);
            return this;
        }

        public WantedAttribute build() {
            notNullOrEmpty(name, Property.NAME);

            return new WantedAttribute(this);
        }

    }

    private static final class Property {

        private static final String NAME = "name";
        private static final String DERIVATION = "derivation";
        private static final String OPTIONAL = "optional";
        private static final String ACCEPT_SELF_ASSERTED = "accept_self_asserted";
        private static final String CONSTRAINTS = "constraints";
        private static final String ALTERNATIVE_NAMES = "alternative_names";

        private Property() { }

    }

}
