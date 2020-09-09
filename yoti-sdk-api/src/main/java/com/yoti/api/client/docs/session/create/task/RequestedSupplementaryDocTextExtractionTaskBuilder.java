package com.yoti.api.client.docs.session.create.task;

public interface RequestedSupplementaryDocTextExtractionTaskBuilder {

    /**
     * Requires that the Task is always followed by a manual TextDataCheck
     *
     * @return the builder
     */
    RequestedSupplementaryDocTextExtractionTaskBuilder withManualCheckAlways();

    /**
     * Requires that only failed Tasks are followed by a manual TextDataCheck
     *
     * @return the builder
     */
    RequestedSupplementaryDocTextExtractionTaskBuilder withManualCheckFallback();

    /**
     * The TextExtractionTask will never fallback to a manual TextDataCheck
     *
     * @return the builder
     */
    RequestedSupplementaryDocTextExtractionTaskBuilder withManualCheckNever();

    RequestedSupplementaryDocTextExtractionTask<?> build();

}
