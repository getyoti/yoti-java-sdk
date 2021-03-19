package com.yoti.api.client.docs.session.create.check;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedWatchlistScreeningConfig implements RequestedCheckConfig {

    @JsonProperty("categories")
    private final List<String> categories;

    RequestedWatchlistScreeningConfig(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

}
