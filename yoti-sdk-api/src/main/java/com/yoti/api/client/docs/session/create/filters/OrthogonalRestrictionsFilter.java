package com.yoti.api.client.docs.session.create.filters;

public interface OrthogonalRestrictionsFilter extends DocumentFilter {

    CountryRestriction getCountryRestriction();

    TypeRestriction getTypeRestriction();

}
