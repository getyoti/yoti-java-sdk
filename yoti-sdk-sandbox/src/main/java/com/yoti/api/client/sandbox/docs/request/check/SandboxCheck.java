package com.yoti.api.client.sandbox.docs.request.check;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class SandboxCheck {

    @JsonProperty("result")
    private final SandboxCheckResult result;

    SandboxCheck(SandboxCheckResult result) {
        this.result = result;
    }

    public SandboxCheckResult getResult() {
        return result;
    }

}
