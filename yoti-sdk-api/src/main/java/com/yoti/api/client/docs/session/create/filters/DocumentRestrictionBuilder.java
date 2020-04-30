package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface DocumentRestrictionBuilder {

    DocumentRestrictionBuilder withCountries(List<String> countryCodes);

    DocumentRestrictionBuilder withDocumentTypes(List<String> documentTypes);

    DocumentRestriction build();

}
