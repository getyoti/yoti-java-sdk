package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class SimpleCountryRestriction implements CountryRestriction {

    @JsonProperty("inclusion")
    private String inclusion;

    @JsonProperty("country_codes")
    private List<String> countryCodes;

    SimpleCountryRestriction(String inclusion, List<String> countryCodes) {
        notNull(inclusion, "inclusion");
        notNull(countryCodes, "countryCodes");
        this.inclusion = inclusion;
        this.countryCodes = countryCodes;
    }

    @Override
    public String getInclusion() {
        return inclusion;
    }

    @Override
    public List<String> getCountryCodes() {
        return countryCodes;
    }

}
