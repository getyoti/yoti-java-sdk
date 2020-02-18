package com.yoti.api.client.docs.session.create;

import java.util.ServiceLoader;

public abstract class SdkConfigBuilderFactory {

    public static SdkConfigBuilderFactory newInstance() {
        ServiceLoader<SdkConfigBuilderFactory> sdkConfigBuilderFactoryServiceLoader = ServiceLoader.load(SdkConfigBuilderFactory.class);
        if (!sdkConfigBuilderFactoryServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + SdkConfigBuilderFactory.class.getSimpleName());
        }
        return sdkConfigBuilderFactoryServiceLoader.iterator().next();
    }

    public abstract SdkConfigBuilder create();

}
