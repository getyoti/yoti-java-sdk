package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchConfigResponse {

    @JsonProperty("categories")
    private List<String> categories;

    public List<String> getCategories() {
        return categories;
    }

}
