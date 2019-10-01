package com.yoti.api.client.docs.session.create;

/**
 * The response to a successful CreateSession call
 */
public interface CreateSessionResult {

    /**
     * Returns the time-to-live (TTL) for the client session
     * token for the created session
     *
     * @return the client session token TTL
     */
    int getClientSessionTokenTtl();

    /**
     * Returns the client session token for the created session
     *
     * @return the client session token
     */
    String getClientSessionToken();

    /**
     * Session ID of the created session
     *
     * @return the session id
     */
    String getSessionId();

}
