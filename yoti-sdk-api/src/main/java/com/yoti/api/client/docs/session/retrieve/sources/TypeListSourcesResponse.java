package com.yoti.api.client.docs.session.retrieve.sources;

import java.util.List;

import com.yoti.api.client.docs.session.retrieve.CaSourcesResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeListSourcesResponse extends CaSourcesResponse {

    @JsonProperty("types")
    private List<String> types;

    public List<String> getTypes() {
        return types;
    }

}
