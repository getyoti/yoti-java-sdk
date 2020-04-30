package com.yoti.api.client.docs.session.create.filters;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

class SimpleOrthogonalRestrictionsFilter implements OrthogonalRestrictionsFilter {

    @JsonProperty("country_restriction")
    private CountryRestriction countryRestriction;

    @JsonProperty("type_restriction")
    private TypeRestriction typeRestriction;

    SimpleOrthogonalRestrictionsFilter(CountryRestriction countryRestriction, TypeRestriction typeRestriction) {
        this.countryRestriction = countryRestriction;
        this.typeRestriction = typeRestriction;
    }

    @Override
    public CountryRestriction getCountryRestriction() {
        return countryRestriction;
    }

    @Override
    public TypeRestriction getTypeRestriction() {
        return typeRestriction;
    }

    @Override
    public String getType() {
        return DocScanConstants.ORTHOGONAL_RESTRICTIONS;
    }

}
