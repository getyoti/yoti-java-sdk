package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

public class NoAuthStrategy implements AuthStrategy {

    @Override
    public List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException {
        return Collections.emptyList();
    }

    @Override
    public List<NameValuePair> createQueryParams() {
        return Collections.emptyList();
    }

}
