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

    DocumentRestrictionsFilter(String inclusion, List<DocumentRestriction> documents, Boolean allowNonLatinDocuments) {
        super(DocScanConstants.DOCUMENT_RESTRICTIONS, allowNonLatinDocuments);
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
        private Boolean allowNonLatinDocuments;

        private Builder() {
            this.documents = new ArrayList<>();
        }

        /**
         * Sets the inclusion to allow documents listed in {@link DocumentRestriction}
         *
         * @return the builder
         */
        public Builder forWhitelist() {
            this.inclusion = DocScanConstants.INCLUSION_WHITELIST;
            return this;
        }

        /**
         * Sets the inclusion not to allow documents listed in {@link DocumentRestriction}
         *
         * @return the builder
         */
        public Builder forBlacklist() {
            this.inclusion = DocScanConstants.INCLUSION_BLACKLIST;
            return this;
        }

        /**
         * Sets the document restrictions by country codes and document types
         *
         * @param countryCodes the list of country codes
         * @param documentTypes the list of document types
         * @return the builder
         */
        public Builder withDocumentRestriction(List<String> countryCodes, List<String> documentTypes) {
            this.documents.add(new DocumentRestriction(countryCodes, documentTypes));
            return this;
        }

        /**
         * Sets the document restrictions by provided {@link DocumentRestriction}
         *
         * @param documentRestriction the {@code DocumentRestriction}
         * @return the builder
         */
        public Builder withDocumentRestriction(DocumentRestriction documentRestriction) {
            this.documents.add(documentRestriction);
            return this;
        }

        /**
         * Sets the flag whether allow non latin documents or not
         *
         * @param allowNonLatinDocuments the flag
         * @return the builder
         */
        public Builder withAllowExpiredDocuments(boolean allowNonLatinDocuments) {
            this.allowNonLatinDocuments = allowNonLatinDocuments;
            return this;
        }

        public DocumentRestrictionsFilter build() {
            notNullOrEmpty(inclusion, "inclusion");
            return new DocumentRestrictionsFilter(inclusion, documents, allowNonLatinDocuments);
        }

    }

}
