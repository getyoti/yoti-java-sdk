package com.yoti.api.client.sandbox.docs.request.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxLivenessCheck extends SandboxCheck {

    @JsonProperty("liveness_type")
    private final String livenessType;

    SandboxLivenessCheck(SandboxCheckResult result, String livenessType) {
        super(result);
        this.livenessType = livenessType;
    }

    public static SandboxZoomLivenessCheckBuilder forZoomLiveness() {
        return new SandboxZoomLivenessCheckBuilder();
    }

    public String getLivenessType() {
        return livenessType;
    }

}
