package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrthogonalRestrictionsFilter extends DocumentFilter {

    @JsonProperty("country_restriction")
    private final CountryRestriction countryRestriction;

    @JsonProperty("type_restriction")
    private final TypeRestriction typeRestriction;

    OrthogonalRestrictionsFilter(CountryRestriction countryRestriction, TypeRestriction typeRestriction) {
        super(DocScanConstants.ORTHOGONAL_RESTRICTIONS);
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

        private Builder() {}

        public Builder withWhitelistedCountries(List<String> countryCodes) {
            countryRestriction = new CountryRestriction(DocScanConstants.INCLUSION_WHITELIST, countryCodes);
            return this;
        }

        public Builder withBlacklistedCountries(List<String> countryCodes) {
            countryRestriction = new CountryRestriction(DocScanConstants.INCLUSION_BLACKLIST, countryCodes);
            return this;
        }

        public Builder withWhitelistedDocumentTypes(List<String> documentTypes) {
            typeRestriction = new TypeRestriction(DocScanConstants.INCLUSION_WHITELIST, documentTypes);
            return this;
        }

        public Builder withBlacklistedDocumentTypes(List<String> documentTypes) {
            typeRestriction = new TypeRestriction(DocScanConstants.INCLUSION_BLACKLIST, documentTypes);
            return this;
        }

        public OrthogonalRestrictionsFilter build() {
            return new OrthogonalRestrictionsFilter(countryRestriction, typeRestriction);
        }
        
    }

}
