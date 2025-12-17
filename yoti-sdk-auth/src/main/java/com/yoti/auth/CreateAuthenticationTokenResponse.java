package com.yoti.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CreateAuthenticationTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("scope")
    private String scope;

    /**
     * Returns the Yoti Authentication token used to perform requests to other Yoti services.
     *
     * @return the newly created access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Returns the type of the newly generated authentication token.
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Returns the amount of time (in seconds) in which the newly generated Authentication Token
     * will expire in.
     *
     * @return the time (in seconds) of when the token will expire
     */
    public Integer getExpiresIn() {
        return expiresIn;
    }

    /**
     * A whitespace delimited string of scopes that the Authentication token has.
     *
     * @return the scopes of the token as a whitespace delimited string
     */
    public String getScope() {
        return scope;
    }

}
