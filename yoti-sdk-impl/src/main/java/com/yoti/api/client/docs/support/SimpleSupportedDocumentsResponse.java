package com.yoti.api.client.docs.support;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SimpleSupportedDocumentsResponse implements SupportedDocumentsResponse {

    @JsonProperty("supported_countries")
    List<SimpleSupportedCountry> supportedCountries;

    @Override
    public List<? extends SupportedCountry> getSupportedCountries() {
        return supportedCountries;
    }

}
