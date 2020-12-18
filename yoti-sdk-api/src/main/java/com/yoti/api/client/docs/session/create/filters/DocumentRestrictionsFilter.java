package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentRestrictionsFilter extends DocumentFilter {

    @JsonProperty("inclusion")
    private final String inclusion;

    @JsonProperty("documents")
    private final List<DocumentRestriction> documents;

    DocumentRestrictionsFilter(String inclusion, List<DocumentRestriction> documents) {
        super(DocScanConstants.DOCUMENT_RESTRICTIONS);
        this.inclusion = inclusion;
        this.documents = documents;
    }

    public static DocumentRestrictionsFilter.Builder builder() {
        return new DocumentRestrictionsFilter.Builder();
    }

    public String getInclusion() {
        return inclusion;
    }

    public List<DocumentRestriction> getDocuments() {
        return documents;
    }

    public static class Builder {

        private String inclusion;
        private final List<DocumentRestriction> documents;

        private Builder() {
            this.documents = new ArrayList<>();
        }

        public Builder forWhitelist() {
            this.inclusion = DocScanConstants.INCLUSION_WHITELIST;
            return this;
        }

        public Builder forBlacklist() {
            this.inclusion = DocScanConstants.INCLUSION_BLACKLIST;
            return this;
        }

        public Builder withDocumentRestriction(List<String> countryCodes, List<String> documentTypes) {
            this.documents.add(new DocumentRestriction(countryCodes, documentTypes));
            return this;
        }

        public Builder withDocumentRestriction(DocumentRestriction documentRestriction) {
            this.documents.add(documentRestriction);
            return this;
        }

        public DocumentRestrictionsFilter build() {
            notNullOrEmpty(inclusion, "inclusion");
            return new DocumentRestrictionsFilter(inclusion, documents);
        }

    }

}
