package com.yoti.api.client.docs.session.create.objective;

import java.util.ServiceLoader;

public abstract class ObjectiveBuilderFactory {

    /**
     * Returns a new instance of a {@link ObjectiveBuilderFactory} using
     * Java's service loader to find the first available implementation
     *
     * @return the factory
     */
    public static ObjectiveBuilderFactory newInstance() {
        ServiceLoader<ObjectiveBuilderFactory> serviceLoader = ServiceLoader.load(ObjectiveBuilderFactory.class);
        if (!serviceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + ObjectiveBuilderFactory.class.getSimpleName());
        }
        return serviceLoader.iterator().next();
    }

    public abstract ProofOfAddressObjectiveBuilder forProofOfAddress();

}
