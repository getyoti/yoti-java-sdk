package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

public interface CountryRestriction {

    String getInclusion();

    List<String> getCountryCodes();

}
