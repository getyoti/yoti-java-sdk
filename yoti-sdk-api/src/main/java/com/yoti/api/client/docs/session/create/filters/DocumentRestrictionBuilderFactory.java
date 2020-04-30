package com.yoti.api.client.docs.session.create.filters;

import java.util.ServiceLoader;

public abstract class DocumentRestrictionBuilderFactory {

    /**
     * Returns a new instance of a {@link DocumentRestrictionBuilderFactory} using
     * Java's service loader to find the first available implementation
     *
     * @return the factory
     */
    public static DocumentRestrictionBuilderFactory newInstance() {
        ServiceLoader<DocumentRestrictionBuilderFactory> serviceLoader = ServiceLoader.load(DocumentRestrictionBuilderFactory.class);
        if (!serviceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + DocumentRestrictionBuilderFactory.class.getSimpleName());
        }
        return serviceLoader.iterator().next();
    }

    public abstract DocumentRestrictionBuilder forDocumentRestriction();

}
