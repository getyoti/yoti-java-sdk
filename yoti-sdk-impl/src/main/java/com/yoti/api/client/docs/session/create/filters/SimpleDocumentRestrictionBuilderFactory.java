package com.yoti.api.client.docs.session.create.filters;

public class SimpleDocumentRestrictionBuilderFactory extends DocumentRestrictionBuilderFactory {

    @Override
    public DocumentRestrictionBuilder forDocumentRestriction() {
        return new SimpleDocumentRestrictionBuilder();
    }

}
