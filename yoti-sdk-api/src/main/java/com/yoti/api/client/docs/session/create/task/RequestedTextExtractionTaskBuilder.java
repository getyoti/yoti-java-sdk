package com.yoti.api.client.docs.session.create.task;

/**
 * Builder to assist creation of {@link RequestedTextExtractionTask}.
 */
public interface RequestedTextExtractionTaskBuilder {

    /**
     * Requires that the Task is always followed by a manual TextDataCheck
     *
     * @return the builder
     */
    RequestedTextExtractionTaskBuilder withManualCheckAlways();

    /**
     * Requires that only failed Tasks are followed by a manual TextDataCheck
     *
     * @return the builder
     */
    RequestedTextExtractionTaskBuilder withManualCheckFallback();

    /**
     * The TextExtractionTask will never fallback to a manual TextDataCheck
     *
     * @return the builder
     */
    RequestedTextExtractionTaskBuilder withManualCheckNever();

    RequestedTextExtractionTask build();

}
