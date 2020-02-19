package com.yoti.api.client.docs.support;

import java.util.List;

public interface SupportedCountry {

    String getCode();

    List<? extends SupportedDocument> getSupportedDocuments();

}
