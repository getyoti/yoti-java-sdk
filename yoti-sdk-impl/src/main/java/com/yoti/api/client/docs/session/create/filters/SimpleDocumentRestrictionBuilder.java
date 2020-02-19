package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public class SimpleDocumentRestrictionBuilder implements DocumentRestrictionBuilder {

    private List<String> countryCodes;
    private List<String> documentTypes;

    @Override
    public DocumentRestrictionBuilder withCountries(List<String> countryCodes) {
        this.countryCodes = countryCodes;
        return this;
    }

    @Override
    public DocumentRestrictionBuilder withDocumentTypes(List<String> documentTypes) {
        this.documentTypes = documentTypes;
        return this;
    }

    @Override
    public DocumentRestriction build() {
        return new SimpleDocumentRestriction(countryCodes, documentTypes);
    }

}
