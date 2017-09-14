package com.yoti.api.spring.security.web;

import com.yoti.api.spring.security.auth.YotiAuthenticationToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class YotiAuthenticationFilterTest {

    private static final String TOKEN_PARAM = "token";
    private static final String TOKEN = "abc";
    private static final String UNKNOWN = "unknown";
    private static final String LOGIN = "/login";

    private YotiAuthenticationFilter filter;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private AuthenticationManager mockManager;

    @Mock
    private AuthenticationDetailsSource<HttpServletRequest, ?> mockDetailsSource;

    @Before
    public void setUp() {
        filter = new YotiAuthenticationFilter(mockManager, LOGIN);
        filter.setAuthenticationDetailsSource(mockDetailsSource);
        initMocks();
    }

    @Test
    public void attemptAuthentication() {
        final YotiAuthenticationToken actualToken = doAuthenticationAndCaptureToken();
        verifyExpectationsOnToken(actualToken);
        verifyMockInteractions();
    }

    private void verifyExpectationsOnToken(final YotiAuthenticationToken actualToken) {
        assertThat(actualToken.getCredentials(), is(TOKEN));
        assertThat(actualToken.getPrincipal(), is(UNKNOWN));
        assertThat(actualToken.isAuthenticated(), is(false));
        assertThat(actualToken.getAuthorities(), is(emptyIterable()));
        assertThat(actualToken.getDetails(), is(nullValue()));
        assertThat(actualToken.getName(), is(UNKNOWN));
    }

    private void verifyMockInteractions() {
        verify(mockRequest).getParameter(TOKEN_PARAM);
        verifyNoMoreInteractions(mockManager);
        verifyNoMoreInteractions(mockRequest);
        verifyNoMoreInteractions(mockResponse);
    }

    private YotiAuthenticationToken doAuthenticationAndCaptureToken() {
        filter.attemptAuthentication(mockRequest, mockResponse);
        final ArgumentCaptor<YotiAuthenticationToken> argumentCaptor = ArgumentCaptor.forClass(YotiAuthenticationToken.class);
        verify(mockManager).authenticate(argumentCaptor.capture());
        return argumentCaptor.getValue();
    }

    private void initMocks() {
        when(mockRequest.getParameter(TOKEN_PARAM)).thenReturn(TOKEN);
        when(mockDetailsSource.buildDetails(any(HttpServletRequest.class))).thenReturn(null);
    }
}
