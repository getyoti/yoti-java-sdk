package com.yoti.api.spring.security.auth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class YotiAuthenticationTokenTest {

    private static final String SAMPLE_TOKEN = "ABC123";
    private static final String UNKNOWN = "unknown";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getCredentials() {
        final YotiAuthenticationToken token = new YotiAuthenticationToken(SAMPLE_TOKEN);
        assertThat(token.getCredentials(), is(SAMPLE_TOKEN));
    }

    @Test
    public void getPrincipal() {
        final YotiAuthenticationToken token = new YotiAuthenticationToken(SAMPLE_TOKEN);
        assertThat(token.getPrincipal(), is(UNKNOWN));
    }

    @Test
    public void setAuthenticatedToTrueIsNotAllowed() {
        final YotiAuthenticationToken token = new YotiAuthenticationToken(SAMPLE_TOKEN);
        expectedException.expect(IllegalArgumentException.class);
        token.setAuthenticated(true);
    }

    @Test
    public void setAuthenticatedToFalseIsAllowed() {
        final YotiAuthenticationToken token = new YotiAuthenticationToken(SAMPLE_TOKEN);
        token.setAuthenticated(false);
        assertThat(token.isAuthenticated(), is(false));
    }

    @Test
    public void defaultTokenIsNotAuthenticated() {
        final YotiAuthenticationToken token = new YotiAuthenticationToken(SAMPLE_TOKEN);
        assertThat(token.isAuthenticated(), is(false));
    }

    @Test
    public void eraseCredentialsShouldRemoveToken() {
        final YotiAuthenticationToken token = new YotiAuthenticationToken(SAMPLE_TOKEN);
        token.eraseCredentials();
        assertThat(token.getCredentials(), nullValue());
    }

}
