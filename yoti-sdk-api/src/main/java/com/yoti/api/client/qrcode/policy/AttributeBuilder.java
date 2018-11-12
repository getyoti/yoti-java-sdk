package com.yoti.api.client.qrcode.policy;

import java.util.List;
import java.util.ServiceLoader;

public class AttributeBuilder {

    private String name;

    private List<String> anchors;

    private String derivation;

    private boolean optional;

    public AttributeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AttributeBuilder anchors(List<String> anchors) {
        this.anchors = anchors;
        return this;
    }

    public AttributeBuilder derivation(String derivation) {
        this.derivation = derivation;
        return this;
    }

    public AttributeBuilder optional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public WantedAttribute build() {
        ServiceLoader<AttributeFactory> factoryLoader = ServiceLoader.load(AttributeFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of AttributeFactory");
        }
        AttributeFactory attributeFactory = factoryLoader.iterator().next();
        return attributeFactory.create(name, anchors, derivation, optional);
    }

}
