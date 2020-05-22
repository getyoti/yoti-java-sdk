package com.yoti.api.client.sandbox.docs.request.check.report;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxDetail {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    public SandboxDetail(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
