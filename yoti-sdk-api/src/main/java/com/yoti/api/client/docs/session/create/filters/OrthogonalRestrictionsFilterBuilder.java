package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface OrthogonalRestrictionsFilterBuilder {

    OrthogonalRestrictionsFilterBuilder withWhitelistedCountries(List<String> countryCodes);

    OrthogonalRestrictionsFilterBuilder withBlacklistedCountries(List<String> countryCodes);

    OrthogonalRestrictionsFilterBuilder withWhitelistedDocumentTypes(List<String> documentTypes);

    OrthogonalRestrictionsFilterBuilder withBlacklistedDocumentTypes(List<String> documentTypes);

    OrthogonalRestrictionsFilter build();

}
