package com.yoti.api.client;

import java.util.List;

public interface AttributeIssuanceDetails {

    DateTime getExpiryDate();

    String getToken();

    List<AttributeDefinition> getIssuingAttributes();

}
