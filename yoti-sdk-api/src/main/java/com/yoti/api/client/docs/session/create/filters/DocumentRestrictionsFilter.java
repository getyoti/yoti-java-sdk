package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface DocumentRestrictionsFilter extends DocumentFilter {

    String getInclusion();

    List<DocumentRestriction> getDocuments();

}
