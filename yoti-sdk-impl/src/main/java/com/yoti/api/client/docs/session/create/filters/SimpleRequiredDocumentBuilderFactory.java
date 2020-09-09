package com.yoti.api.client.docs.session.create.filters;

public class SimpleRequiredDocumentBuilderFactory extends RequiredDocumentBuilderFactory {

    @Override
    public RequiredIdDocumentBuilder forIdDocument() {
        return new SimpleRequiredIdDocumentBuilder();
    }

    @Override
    public RequiredSupplementaryDocumentBuilder forSupplementaryDocument() {
        return new SimpleRequiredSupplementaryDocumentBuilder();
    }

}
