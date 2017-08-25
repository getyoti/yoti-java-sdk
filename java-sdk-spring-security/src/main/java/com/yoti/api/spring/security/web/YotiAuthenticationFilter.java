package com.yoti.api.spring.security.web;

import com.yoti.api.spring.security.auth.YotiAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet filter which can use used to intercept the Yoti Authentication token and proceed with authentication via Spring Security constructs.
 */
public class YotiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(YotiAuthenticationFilter.class);
    private static final String TOKEN_PARAM_NAME = "token";

    public YotiAuthenticationFilter(final AuthenticationManager authenticationManager, final String authenticationPath) {
        super(new AntPathRequestMatcher(authenticationPath, HttpMethod.GET.name(), false));
        setAuthenticationManager(authenticationManager);
    }

    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        LOG.debug("Yoti Auth Filter Running....");
        final String token = obtainToken(request);
        LOG.debug("Found token {}.", token);

        final YotiAuthenticationToken authRequest = new YotiAuthenticationToken(token);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainToken(final HttpServletRequest request) {
        return request.getParameter(TOKEN_PARAM_NAME);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(final HttpServletRequest request, final YotiAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
