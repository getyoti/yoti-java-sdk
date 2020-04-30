package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

class SimpleOrthogonalRestrictionsFilterBuilder implements OrthogonalRestrictionsFilterBuilder {

    private CountryRestriction countryRestriction;
    private TypeRestriction typeRestriction;

    @Override
    public OrthogonalRestrictionsFilterBuilder withWhitelistedCountries(List<String> countryCodes) {
        countryRestriction = new SimpleCountryRestriction(DocScanConstants.INCLUSION_WHITELIST, countryCodes);
        return this;
    }

    @Override
    public OrthogonalRestrictionsFilterBuilder withBlacklistedCountries(List<String> countryCodes) {
        countryRestriction = new SimpleCountryRestriction(DocScanConstants.INCLUSION_BLACKLIST, countryCodes);
        return this;
    }

    @Override
    public OrthogonalRestrictionsFilterBuilder withWhitelistedDocumentTypes(List<String> documentTypes) {
        typeRestriction = new SimpleTypeRestriction(DocScanConstants.INCLUSION_WHITELIST, documentTypes);
        return this;
    }

    @Override
    public OrthogonalRestrictionsFilterBuilder withBlacklistedDocumentTypes(List<String> documentTypes) {
        typeRestriction = new SimpleTypeRestriction(DocScanConstants.INCLUSION_BLACKLIST, documentTypes);
        return this;
    }

    @Override
    public OrthogonalRestrictionsFilter build() {
        return new SimpleOrthogonalRestrictionsFilter(countryRestriction, typeRestriction);
    }

}
