package com.yoti.api.client.shareurl.policy;

import java.util.ServiceLoader;

public abstract class WantedAnchorBuilder {

    public static final WantedAnchorBuilder newInstance() {
        ServiceLoader<WantedAnchorBuilder> wantedAnchorBuilderServiceLoader = ServiceLoader.load(WantedAnchorBuilder.class);
        if (!wantedAnchorBuilderServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + WantedAttributeBuilder.class.getSimpleName());
        }
        WantedAnchorBuilder wantedAnchorBuilder = wantedAnchorBuilderServiceLoader.iterator().next();
        return wantedAnchorBuilder.createWantedAnchorBuilder();
    }

    protected abstract WantedAnchorBuilder createWantedAnchorBuilder();

    public abstract WantedAnchorBuilder withValue(String value);

    public abstract WantedAnchorBuilder withSubType(String subType);

    public abstract WantedAnchor build();

}
