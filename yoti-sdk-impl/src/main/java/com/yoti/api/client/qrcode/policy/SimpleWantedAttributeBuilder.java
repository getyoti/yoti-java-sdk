package com.yoti.api.client.qrcode.policy;

public class SimpleWantedAttributeBuilder extends WantedAttributeBuilder {

    private String name;
    private String derivation;
    private boolean optional;

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
    public WantedAttribute build() {
        return new SimpleWantedAttribute(name, derivation, optional);
    }

}
