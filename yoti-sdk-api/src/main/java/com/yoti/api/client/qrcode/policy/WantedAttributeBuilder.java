package com.yoti.api.client.qrcode.policy;

import java.util.ServiceLoader;

public class WantedAttributeBuilder {

    private String name;

    private String derivation;

    private boolean optional;

    public WantedAttributeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WantedAttributeBuilder derivation(String derivation) {
        this.derivation = derivation;
        return this;
    }

    public WantedAttributeBuilder optional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public WantedAttribute build() {
        ServiceLoader<WantedAttributeFactory> factoryLoader = ServiceLoader.load(WantedAttributeFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of WantedAttributeFactory");
        }
        WantedAttributeFactory wantedAttributeFactory = factoryLoader.iterator().next();
        return wantedAttributeFactory.create(name, derivation, optional);
    }

}
