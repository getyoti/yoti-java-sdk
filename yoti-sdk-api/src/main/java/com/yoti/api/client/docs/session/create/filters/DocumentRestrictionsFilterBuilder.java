package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface DocumentRestrictionsFilterBuilder {

    DocumentRestrictionsFilterBuilder forWhitelist();

    DocumentRestrictionsFilterBuilder forBlacklist();

    DocumentRestrictionsFilterBuilder withDocumentRestriction(List<String> countryCodes, List<String> documentTypes);

    DocumentRestrictionsFilterBuilder withDocumentRestriction(DocumentRestriction documentRestriction);

    DocumentRestrictionsFilter build();

}
