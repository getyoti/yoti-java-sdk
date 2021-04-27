package com.yoti.api.client.docs.session.create.check.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedSearchProfileSources extends RequestedCaSources {

    @JsonProperty("search_profile")
    private final String searchProfile;

    RequestedSearchProfileSources(String searchProfile) {
        this.searchProfile = searchProfile;
    }

    public static RequestedSearchProfileSources.Builder builder() {
        return new RequestedSearchProfileSources.Builder();
    }

    public String getSearchProfile() {
        return searchProfile;
    }

    public static class Builder {

        private String searchProfile;

        private Builder() {}

        public Builder withSearchProfile(String searchProfile) {
            this.searchProfile = searchProfile;
            return this;
        }

        public RequestedSearchProfileSources build() {
            return new RequestedSearchProfileSources(searchProfile);
        }
    }
}
