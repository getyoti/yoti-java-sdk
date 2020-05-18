package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

import java.util.ArrayList;
import java.util.List;

class SimpleDocumentRestrictionsFilterBuilder implements DocumentRestrictionsFilterBuilder {

    private String inclusion;
    private List<DocumentRestriction> documents = new ArrayList<>();

    @Override
    public DocumentRestrictionsFilterBuilder forWhitelist() {
        this.inclusion = DocScanConstants.INCLUSION_WHITELIST;
        return this;
    }

    @Override
    public DocumentRestrictionsFilterBuilder forBlacklist() {
        this.inclusion = DocScanConstants.INCLUSION_BLACKLIST;
        return this;
    }

    @Override
    public DocumentRestrictionsFilterBuilder withDocumentRestriction(List<String> countryCodes, List<String> documentTypes) {
        this.documents.add(new SimpleDocumentRestriction(countryCodes, documentTypes));
        return this;
    }

    @Override
    public DocumentRestrictionsFilterBuilder withDocumentRestriction(DocumentRestriction documentRestriction) {
        this.documents.add(documentRestriction);
        return this;
    }

    @Override
    public DocumentRestrictionsFilter build() {
        notNullOrEmpty(inclusion, "inclusion");
        return new SimpleDocumentRestrictionsFilter(inclusion, documents);
    }

}
