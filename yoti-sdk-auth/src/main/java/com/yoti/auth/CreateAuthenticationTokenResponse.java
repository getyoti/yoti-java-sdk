package com.yoti.auth;

public final class CreateAuthenticationTokenResponse {

    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
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
