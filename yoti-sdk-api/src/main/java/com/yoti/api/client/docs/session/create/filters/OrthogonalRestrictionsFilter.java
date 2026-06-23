package com.yoti.api.client.docs.session.create.filters;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrthogonalRestrictionsFilter extends DocumentFilter {

    @JsonProperty("country_restriction")
    private final CountryRestriction countryRestriction;

    @JsonProperty("type_restriction")
    private final TypeRestriction typeRestriction;

    private OrthogonalRestrictionsFilter(CountryRestriction countryRestriction, TypeRestriction typeRestriction, Boolean allowNonLatinDocuments,
            Boolean allowExpiredDocuments, Boolean allowDigitalIds, List<AllowedProviderPayload> allowedProviders) {
        super(DocScanConstants.ORTHOGONAL_RESTRICTIONS, allowNonLatinDocuments, allowExpiredDocuments, allowDigitalIds, allowedProviders);
        this.countryRestriction = countryRestriction;
        this.typeRestriction = typeRestriction;
    }

    public static OrthogonalRestrictionsFilter.Builder builder() {
        return new OrthogonalRestrictionsFilter.Builder();
    }

    public CountryRestriction getCountryRestriction() {
        return countryRestriction;
    }

    public TypeRestriction getTypeRestriction() {
        return typeRestriction;
    }

    public static class Builder {

        private CountryRestriction countryRestriction;
        private TypeRestriction typeRestriction;
        private Boolean allowNonLatinDocuments;
        private Boolean allowExpiredDocuments;
        private Boolean allowDigitalIds;
        private List<AllowedProviderPayload> allowedProviders;

        private Builder() {}

        /**
         * Sets the allowed countries by country codes
         *
         * @param countryCodes the country codes
         * @return the builder
         */
        public Builder withWhitelistedCountries(List<String> countryCodes) {
            countryRestriction = new CountryRestriction(DocScanConstants.INCLUSION_WHITELIST, countryCodes);
            return this;
        }

        /**
         * Sets the disallowed documents by country code
         *
         * @param countryCodes the country codes
         * @return the builder
         */
        public Builder withBlacklistedCountries(List<String> countryCodes) {
            countryRestriction = new CountryRestriction(DocScanConstants.INCLUSION_BLACKLIST, countryCodes);
            return this;
        }

        /**
         * Sets the allowed document types
         *
         * @param documentTypes the document types
         * @return the builder
         */
        public Builder withWhitelistedDocumentTypes(List<String> documentTypes) {
            typeRestriction = new TypeRestriction(DocScanConstants.INCLUSION_WHITELIST, documentTypes);
            return this;
        }

        /**
         * Sets the disallowed document types
         *
         * @param documentTypes the document types
         * @return the builder
         */
        public Builder withBlacklistedDocumentTypes(List<String> documentTypes) {
            typeRestriction = new TypeRestriction(DocScanConstants.INCLUSION_BLACKLIST, documentTypes);
            return this;
        }

        /**
         * Sets the flag whether to allow/disallow non latin documents
         *
         * @param allowNonLatinDocuments the flag
         * @return the builder
         */
        public Builder withAllowNonLatinDocuments(boolean allowNonLatinDocuments) {
            this.allowNonLatinDocuments = allowNonLatinDocuments;
            return this;
        }

        /**
         * Sets the flag to allow/disallow expired documents
         *
         * @param allowExpiredDocuments the flag
         * @return the builder
         */
        public Builder withAllowExpiredDocuments(boolean allowExpiredDocuments) {
            this.allowExpiredDocuments = allowExpiredDocuments;
            return this;
        }

        /**
         * Sets the flag to allow/disallow digital IDs
         *
         * @param allowDigitalIds the flag
         * @return the builder
         */
        public Builder withAllowDigitalIds(boolean allowDigitalIds) {
            this.allowDigitalIds = allowDigitalIds;
            return this;
        }

        /**
         * Sets the list of digital ID providers that are allowed to satisfy the filter
         *
         * @param allowedProviders the allowed providers
         * @return the builder
         */
        public Builder withAllowedProviders(List<AllowedProviderPayload> allowedProviders) {
            this.allowedProviders = allowedProviders;
            return this;
        }

        /**
         * Adds a digital ID provider, by name, to the list of allowed providers
         *
         * @param name the provider name
         * @return the builder
         */
        public Builder withAllowedProvider(String name) {
            if (this.allowedProviders == null) {
                this.allowedProviders = new ArrayList<>();
            }
            this.allowedProviders.add(AllowedProviderPayload.builder().withName(name).build());
            return this;
        }

        public OrthogonalRestrictionsFilter build() {
            return new OrthogonalRestrictionsFilter(countryRestriction, typeRestriction, allowNonLatinDocuments, allowExpiredDocuments, allowDigitalIds, allowedProviders);
        }
        
    }

}
