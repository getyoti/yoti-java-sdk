package com.yoti.api.client.spi.remote.call.factory;

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
    public Header createAuthHeader(String httpMethod, String endpoint, byte[] payload) {
        return new BasicHeader(YotiConstants.AUTHORIZATION_HEADER, "Bearer " + authenticationToken);
    }

    @Override
    public NameValuePair getQueryParam() {
        return null;
    }

}
