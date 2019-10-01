package com.yoti.api.client.docs.session.create.task;

import java.util.ServiceLoader;

/**
 * Factory class used for creating RequestedTaskBuilders
 */
public abstract class RequestedTaskBuilderFactory {

    public static RequestedTaskBuilderFactory newInstance() {
        ServiceLoader<RequestedTaskBuilderFactory> requestedTaskBuilderFactoryServiceLoader = ServiceLoader.load(RequestedTaskBuilderFactory.class);
        if (!requestedTaskBuilderFactoryServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + RequestedTaskBuilderFactory.class.getSimpleName());
        }
        return requestedTaskBuilderFactoryServiceLoader.iterator().next();
    }

    /**
     * Returns a {@link RequestedTextExtractionTaskBuilder} used for
     * creating {@link RequestedTextExtractionTask}
     *
     * @return the builder
     */
    public abstract RequestedTextExtractionTaskBuilder forTextExtractionTask();

}
