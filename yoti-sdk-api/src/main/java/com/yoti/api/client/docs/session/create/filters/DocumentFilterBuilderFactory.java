package com.yoti.api.client.docs.session.create.filters;

import java.util.ServiceLoader;

public abstract class DocumentFilterBuilderFactory {

    /**
     * Returns a new instance of a {@link DocumentFilterBuilderFactory} using
     * Java's service loader to find the first available implementation
     *
     * @return the factory
     */
    public static DocumentFilterBuilderFactory newInstance() {
        ServiceLoader<DocumentFilterBuilderFactory> serviceLoader = ServiceLoader.load(DocumentFilterBuilderFactory.class);
        if (!serviceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + DocumentFilterBuilderFactory.class.getSimpleName());
        }
        return serviceLoader.iterator().next();
    }

    public abstract OrthogonalRestrictionsFilterBuilder forOrthogonalRestrictionsFilter();

    public abstract DocumentRestrictionsFilterBuilder forDocumentRestrictionsFilter();

}
