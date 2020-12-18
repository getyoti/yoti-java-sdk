package com.yoti.api.client.docs.support;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedDocumentsResponse {

    @JsonProperty("supported_countries")
    List<SupportedCountry> supportedCountries;

    public List<? extends SupportedCountry> getSupportedCountries() {
        return supportedCountries;
    }

}
