package com.yoti.api.client.docs.session.retrieve.configuration;

import java.util.List;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.CaptureResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionConfigurationResponse {

    @JsonProperty("client_session_token_ttl")
    private int clientSessionTokenTtl;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("requested_checks")
    private List<String> requestedChecks;

    @JsonProperty("capture")
    private CaptureResponse capture;
//
//    @JsonProperty("sdk_config")
//    private SdkConfigResponse sdkConfig;

    /**
     * Returns the amount of time remaining in seconds until the session
     * expires.
     *
     * @return the client session token TTL
     */
    public int getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    /**
     * The session ID that the configuration belongs to
     *
     * @return the session ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Returns a list of strings, signifying the checks that have been requested
     * in the session
     *
     * @return the requested checks
     */
    public List<String> getRequestedChecks() {
        return requestedChecks;
    }

    /**
     * Returns information about what needs to be captured to fulfill the
     * sessions requirements
     *
     * @return the capture
     */
    public CaptureResponse getCapture() {
        return capture;
    }
//
//    /**
//     * Returns the configuration used by the web/native SDKs
//     *
//     * @return the SDK configuration
//     */
//    public SdkConfigResponse getSdkConfig() {
//        return sdkConfig;
//    }

}
