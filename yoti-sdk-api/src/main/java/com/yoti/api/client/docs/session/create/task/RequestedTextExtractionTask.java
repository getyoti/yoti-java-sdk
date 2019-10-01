package com.yoti.api.client.docs.session.create.task;

/**
 * Requests that a TextExtractionTask be applied to each Document
 *
 * @param <T> class that extends {@link RequestedTextExtractionTaskConfig}
 */
public interface RequestedTextExtractionTask<T extends RequestedTextExtractionTaskConfig> extends RequestedTask<T> {

    @Override
    T getConfig();

}
