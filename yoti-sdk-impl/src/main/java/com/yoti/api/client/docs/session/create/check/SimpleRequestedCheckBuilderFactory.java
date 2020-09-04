package com.yoti.api.client.docs.session.create.check;

public class SimpleRequestedCheckBuilderFactory extends RequestedCheckBuilderFactory {

    @Override
    public RequestedDocumentAuthenticityCheckBuilder forDocumentAuthenticityCheck() {
        return new SimpleRequestedDocumentAuthenticityCheckBuilder();
    }

    @Override
    public RequestedLivenessCheckBuilder forLivenessCheck() {
        return new SimpleRequestedLivenessCheckBuilder();
    }

    @Override
    public RequestedFaceMatchCheckBuilder forFaceMatchCheck() {
        return new SimpleRequestedFaceMatchCheckBuilder();
    }

    @Override
    public RequestedIdDocumentComparisonCheckBuilder forIdDocumentComparisonCheck() {
        return new SimpleRequestedIdDocumentComparisonCheckBuilder();
    }
}
