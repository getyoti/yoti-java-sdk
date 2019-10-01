package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleGetSessionResult implements GetSessionResult {

    @JsonProperty("client_session_token_ttl")
    private long clientSessionTokenTtl;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("user_tracking_id")
    private String userTrackingId;

    @JsonProperty("state")
    private String state;

    @JsonProperty("client_session_token")
    private String clientSessionToken;

    @JsonProperty("checks")
    private List<? extends SimpleCheckResponse> checks;

    @JsonProperty("resources")
    private SimpleResourceContainer resources;

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public long getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getClientSessionToken() {
        return clientSessionToken;
    }

    @Override
    public List<? extends CheckResponse> getChecks() {
        return checks;
    }

    @Override
    public ResourceContainer getResources() {
        return resources;
    }

    @Override
    public String getUserTrackingId() {
        return userTrackingId;
    }
}
