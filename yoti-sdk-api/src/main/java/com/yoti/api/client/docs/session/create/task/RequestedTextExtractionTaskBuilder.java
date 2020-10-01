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

    /**
     * The TextExtractionTask will use chip data if it is available
     *
     * @return the builder
     */
    RequestedTextExtractionTaskBuilder withChipDataDesired();

    /**
     * The TextExtractionTask will ignore chip data
     *
     * @return the builder
     */
    RequestedTextExtractionTaskBuilder withChipDataIgnore();

    RequestedTextExtractionTask build();
}
