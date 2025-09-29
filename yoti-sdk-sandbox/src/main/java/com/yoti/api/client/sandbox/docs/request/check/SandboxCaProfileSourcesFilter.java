package com.yoti.api.client.sandbox.docs.request.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxCaProfileSourcesFilter extends SandboxCaSourcesFilter {

    @JsonProperty("search_profile")
    private String searchProfile;

    SandboxCaProfileSourcesFilter(String searchProfile) {
        this.searchProfile = searchProfile;
    }

    public String getSearchProfile() {
        return searchProfile;
    }

}
