package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface TypeRestriction {

    String getInclusion();

    List<String> getDocumentTypes();

}
