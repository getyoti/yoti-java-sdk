package com.yoti.api.client.docs.session.create;

import java.util.ServiceLoader;

public abstract class SessionSpecBuilderFactory {

    public static SessionSpecBuilderFactory newInstance() {
        ServiceLoader<SessionSpecBuilderFactory> sessionSpecBuilderFactoryServiceLoader = ServiceLoader.load(SessionSpecBuilderFactory.class);
        if (!sessionSpecBuilderFactoryServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + SessionSpecBuilderFactory.class.getSimpleName());
        }
        return sessionSpecBuilderFactoryServiceLoader.iterator().next();
    }

    public abstract SessionSpecBuilder create();

}
