package com.yoti.api.client.sandbox.docs.request.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxLivenessCheck extends SandboxCheck {

    @JsonProperty("liveness_type")
    private final String livenessType;

    @JsonProperty("response_delay")
    private final Integer responseDelay;

    SandboxLivenessCheck(SandboxCheckResult result, String livenessType, Integer responseDelay) {
        super(result);
        this.livenessType = livenessType;
        this.responseDelay = responseDelay;
    }

    public static SandboxZoomLivenessCheckBuilder forZoomLiveness() {
        return new SandboxZoomLivenessCheckBuilder();
    }

    public static SandboxStaticLivenessCheckBuilder forStaticLiveness() { return new SandboxStaticLivenessCheckBuilder(); }

    public String getLivenessType() {
        return livenessType;
    }

}
