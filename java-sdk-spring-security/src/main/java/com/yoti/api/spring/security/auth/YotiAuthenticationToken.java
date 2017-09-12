package com.yoti.api.spring.security.auth;


import org.springframework.security.authentication.AbstractAuthenticationToken;

public final class YotiAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 1L;
    private static final String UNKNOWN = "unknown";

    private String token;

    public YotiAuthenticationToken(final String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    @Override
    public String getCredentials() {
        return this.token;
    }

    @Override
    public String getPrincipal() {
        return UNKNOWN;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted.");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        token = null;
    }
}
