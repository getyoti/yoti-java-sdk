package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response to a successful CreateSession call
 */
public class CreateSessionResult {

    @JsonProperty("client_session_token_ttl")
    private int clientSessionTokenTtl;

    @JsonProperty("client_session_token")
    private String clientSessionToken;

    @JsonProperty("session_id")
    private String sessionId;

    /**
     * Returns the time-to-live (TTL) for the client session
     * token for the created session
     *
     * @return the client session token TTL
     */
    public int getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    /**
     * Returns the client session token for the created session
     *
     * @return the client session token
     */
    public String getClientSessionToken() {
        return clientSessionToken;
    }

    /**
     * Session ID of the created session
     *
     * @return the session id
     */
    public String getSessionId() {
        return sessionId;
    }

}
