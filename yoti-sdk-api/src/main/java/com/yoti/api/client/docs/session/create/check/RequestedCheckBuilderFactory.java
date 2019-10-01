package com.yoti.api.client.docs.session.create.check;

import java.util.ServiceLoader;

/**
 * Factory class used for creating various RequestedCheckBuilders
 */
public abstract class RequestedCheckBuilderFactory {

    /**
     * Returns a new instance of a {@link RequestedCheckBuilderFactory} using
     * Java's service loader to find the first relevant implementation
     *
     * @return the factory
     */
    public static RequestedCheckBuilderFactory newInstance() {
        ServiceLoader<RequestedCheckBuilderFactory> requestedCheckBuilderFactoryServiceLoader = ServiceLoader.load(RequestedCheckBuilderFactory.class);
        if (!requestedCheckBuilderFactoryServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + RequestedCheckBuilderFactory.class.getSimpleName());
        }
        return requestedCheckBuilderFactoryServiceLoader.iterator().next();
    }

    /**
     * Returns a {@link RequestedDocumentAuthenticityCheckBuilder} used for
     * creating {@link RequestedDocumentAuthenticityCheck}
     *
     * @return the builder
     */
    public abstract RequestedDocumentAuthenticityCheckBuilder forDocumentAuthenticityCheck();

    /**
     * Returns a {@link RequestedLivenessCheckBuilder} used for
     * creating {@link RequestedLivenessCheck}
     *
     * @return the builder
     */
    public abstract RequestedLivenessCheckBuilder forLivenessCheck();

    /**
     * Returns a {@link RequestedFaceMatchCheckBuilder} used for
     * creating {@link RequestedFaceMatchCheck}
     *
     * @return the builder
     */
    public abstract RequestedFaceMatchCheckBuilder forFaceMatchCheck();

}
