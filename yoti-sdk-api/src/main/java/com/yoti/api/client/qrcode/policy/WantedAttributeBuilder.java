package com.yoti.api.client.qrcode.policy;

import java.util.ServiceLoader;

public abstract class WantedAttributeBuilder {

    public static final WantedAttributeBuilder newInstance() {
        ServiceLoader<WantedAttributeBuilder> wantedAttributeBuilderLoader = ServiceLoader.load(WantedAttributeBuilder.class);
        if (!wantedAttributeBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + WantedAttributeBuilder.class.getSimpleName());
        }
        WantedAttributeBuilder wantedAttributeBuilder = wantedAttributeBuilderLoader.iterator().next();
        return wantedAttributeBuilder.createWantedAttributeBuilder();
    }

    protected abstract WantedAttributeBuilder createWantedAttributeBuilder();

    public abstract WantedAttributeBuilder withName(String name);

    public abstract WantedAttributeBuilder withDerivation(String derivation);

    public abstract WantedAttributeBuilder withOptional(boolean optional);

    public abstract WantedAttribute build();

}
