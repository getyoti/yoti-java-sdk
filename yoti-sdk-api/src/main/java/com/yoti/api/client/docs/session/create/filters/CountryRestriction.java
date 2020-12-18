package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryRestriction {

    @JsonProperty("inclusion")
    private final String inclusion;

    @JsonProperty("country_codes")
    private final List<String> countryCodes;

    CountryRestriction(String inclusion, List<String> countryCodes) {
        notNull(inclusion, "inclusion");
        notNull(countryCodes, "countryCodes");
        this.inclusion = inclusion;
        this.countryCodes = countryCodes;
    }

    public String getInclusion() {
        return inclusion;
    }

    public List<String> getCountryCodes() {
        return countryCodes;
    }

}
