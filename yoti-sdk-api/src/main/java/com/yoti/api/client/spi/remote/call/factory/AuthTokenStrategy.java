package com.yoti.api.client.spi.remote.call.factory;

import java.util.Collections;
import java.util.List;

import com.yoti.api.client.spi.remote.call.YotiConstants;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

public class AuthTokenStrategy implements AuthStrategy {

    private final String authenticationToken;

    public AuthTokenStrategy(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @Override
    public List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) {
        return Collections.singletonList(new BasicHeader(YotiConstants.AUTHORIZATION_HEADER, "Bearer " + authenticationToken));
    }

    @Override
    public List<NameValuePair> createQueryParams() {
        return Collections.emptyList();
    }

}
