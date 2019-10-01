package com.yoti.api.client.docs.session.create.check;

public class SimpleRequestedDocumentAuthenticityCheckBuilder implements RequestedDocumentAuthenticityCheckBuilder {

    @Override
    public RequestedDocumentAuthenticityCheck build() {
        return new SimpleRequestedDocumentAuthenticityCheck(new SimpleRequestedDocumentAuthenticityConfig());
    }

}
