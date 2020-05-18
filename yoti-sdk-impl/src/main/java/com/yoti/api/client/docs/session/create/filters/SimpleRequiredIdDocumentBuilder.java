package com.yoti.api.client.docs.session.create.filters;

class SimpleRequiredIdDocumentBuilder implements RequiredIdDocumentBuilder {

    private DocumentFilter filter;

    @Override
    public RequiredIdDocumentBuilder withFilter(DocumentFilter filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public RequiredIdDocument build() {
        return new SimpleRequiredIdDocument(filter);
    }

}
