package com.yoti.api.client.sandbox.docs.request.check;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxCaTypeListSourcesFilter extends SandboxCaSourcesFilter {

    @JsonProperty("types")
    private final List<String> types;

    SandboxCaTypeListSourcesFilter(List<String> types) {
        this.types = types;
    }

    public List<String> getTypes() {
        return types;
    }

}
