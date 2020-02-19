package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface DocumentRestriction {

    List<String> getCountryCodes();

    List<String> getDocumentTypes();

}
