package com.yoti.api.client.shareurl.policy;

import com.yoti.api.client.shareurl.constraint.Constraint;

import java.util.Collections;
import java.util.List;

public class SimpleWantedAttributeBuilder extends WantedAttributeBuilder {

    private String name;
    private String derivation;
    private boolean optional;
    private boolean acceptSelfAsserted;
    private List<Constraint> constraints;

    @Override
    protected WantedAttributeBuilder createWantedAttributeBuilder() {
        return new SimpleWantedAttributeBuilder();
    }

    @Override
    public WantedAttributeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public WantedAttributeBuilder withDerivation(String derivation) {
        this.derivation = derivation;
        return this;
    }

    @Override
    public WantedAttributeBuilder withOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    @Override
    public WantedAttributeBuilder withAcceptSelfAsserted(boolean acceptSelfAsserted) {
        this.acceptSelfAsserted = acceptSelfAsserted;
        return this;
    }

    @Override
    public WantedAttributeBuilder withConstraints(List<Constraint> constraints) {
        this.constraints = Collections.unmodifiableList(constraints);
        return this;
    }

    @Override
    public WantedAttribute build() {
        return new SimpleWantedAttribute(name, derivation, optional, acceptSelfAsserted, constraints);
    }

}
