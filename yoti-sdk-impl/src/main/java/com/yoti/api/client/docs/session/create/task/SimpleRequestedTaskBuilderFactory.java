package com.yoti.api.client.docs.session.create.task;

public class SimpleRequestedTaskBuilderFactory extends RequestedTaskBuilderFactory {

    @Override
    public RequestedTextExtractionTaskBuilder forTextExtractionTask() {
        return new SimpleRequestedTextExtractionTaskBuilder();
    }

    @Override
    public RequestedSupplementaryDocTextExtractionTaskBuilder forSupplementaryDocTextExtractionTask() {
        return new SimpleRequestedSupplementaryDocTextExtractionTaskBuilder();
    }

}
