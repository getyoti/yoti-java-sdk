package com.yoti.api.client.docs.session.create.filters;

import java.util.ServiceLoader;

public abstract class RequiredDocumentBuilderFactory {

    /**
     * Returns a new instance of a {@link DocumentFilterBuilderFactory} using
     * Java's service loader to find the first relevant implementation
     *
     * @return the factory
     */
    public static RequiredDocumentBuilderFactory newInstance() {
        ServiceLoader<RequiredDocumentBuilderFactory> serviceLoader = ServiceLoader.load(RequiredDocumentBuilderFactory.class);
        if (!serviceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + RequiredDocumentBuilderFactory.class.getSimpleName());
        }
        return serviceLoader.iterator().next();
    }

    public abstract RequiredIdDocumentBuilder forIdDocument();

}
