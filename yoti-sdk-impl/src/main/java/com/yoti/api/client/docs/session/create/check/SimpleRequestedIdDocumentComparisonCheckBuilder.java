package com.yoti.api.client.docs.session.create.check;

public class SimpleRequestedIdDocumentComparisonCheckBuilder implements RequestedIdDocumentComparisonCheckBuilder {

    @Override
    public RequestedIdDocumentComparisonCheck build() {
        return new SimpleRequestedIdDocumentComparisonCheck(new SimpleRequestedIdDocumentComparisonConfig());
    }
}
