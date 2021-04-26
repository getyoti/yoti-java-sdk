package com.yoti.api.client.docs.session.retrieve.sources;

import com.yoti.api.client.docs.session.retrieve.CaSourcesResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchProfileSourcesResponse extends CaSourcesResponse {

    @JsonProperty("search_profile")
    private String searchProfile;

    public String getSearchProfile() {
        return searchProfile;
    }

}
