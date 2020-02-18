package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleCreateSessionResult implements CreateSessionResult {

    @JsonProperty("client_session_token_ttl")
    private int clientSessionTokenTtl;

    @JsonProperty("client_session_token")
    private String clientSessionToken;

    @JsonProperty("session_id")
    private String sessionId;

    @Override
    public int getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    @Override
    public String getClientSessionToken() {
        return clientSessionToken;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

}
