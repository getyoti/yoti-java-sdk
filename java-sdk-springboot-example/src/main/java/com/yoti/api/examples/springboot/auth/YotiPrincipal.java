package com.yoti.api.examples.springboot.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public final class YotiPrincipal extends AbstractAuthenticationToken {

    public static final long serialVersionUID = 1L;

    private final String rememberMeId;
    private final String name;

    YotiPrincipal(final String name, final String rememberMeId, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.rememberMeId = rememberMeId;
        this.name = name;
    }

    @Override
    public Object getCredentials() {
        return rememberMeId;
    }

    @Override
    public Object getPrincipal() {
        return rememberMeId;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRememberMeId() {
        return rememberMeId;
    }
}
