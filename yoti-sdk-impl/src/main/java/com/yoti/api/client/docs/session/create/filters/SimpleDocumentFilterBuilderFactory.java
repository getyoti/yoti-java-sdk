package com.yoti.api.client.docs.session.create.filters;

public class SimpleDocumentFilterBuilderFactory extends DocumentFilterBuilderFactory {

    @Override
    public OrthogonalRestrictionsFilterBuilder forOrthogonalRestrictionsFilter() {
        return new SimpleOrthogonalRestrictionsFilterBuilder();
    }

    @Override
    public DocumentRestrictionsFilterBuilder forDocumentRestrictionsFilter() {
        return new SimpleDocumentRestrictionsFilterBuilder();
    }

}
