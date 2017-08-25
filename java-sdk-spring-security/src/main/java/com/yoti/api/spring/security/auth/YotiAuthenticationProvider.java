package com.yoti.api.spring.security.auth;


import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.spring.security.service.YotiUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * An authentication provider which uses a Yoti Client and Yoti User Service to decrypt a token and return an authenticated principal.
 */
public class YotiAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(YotiAuthenticationProvider.class);

    private final YotiClient client;
    private final YotiUserService yotiUserService;

    /**
     * Creates a new instance of the provider.
     *
     * @param client          the (non-null) Yoti Client to use to decrypt tokens.
     * @param yotiUserService the (non-null) service to use to match activity details with a user in your domain.
     */
    public YotiAuthenticationProvider(final YotiClient client, final YotiUserService yotiUserService) {
        this.client = client;
        this.yotiUserService = yotiUserService;
    }

    /**
     * Authenticates the provided token.
     *
     * @param authentication the token to authenticate.
     * @return the authenticated user.
     * @throws AuthenticationException if the token could not be authenticated.
     */
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (authentication instanceof YotiAuthenticationToken) {
            LOG.debug("Processing Yoti Authentication Token.");
            return doAuthentication((YotiAuthenticationToken) authentication);
        } else {
            throw new IllegalArgumentException("Invalid token type.");
        }
    }

    private Authentication doAuthentication(final YotiAuthenticationToken token) {
        try {
            return yotiUserService.login(client.getActivityDetails(token.getCredentials()));
        } catch (final ProfileException e) {
            throw new InternalAuthenticationServiceException("Unable to extract Yoti profile.", e);
        } catch (final RuntimeException e) {
            throw new InternalAuthenticationServiceException("A problem occurred while trying to authenticate the user.", e);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (YotiAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
