package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

public interface AuthStrategy {

    Header createAuthHeader(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException;

    NameValuePair getQueryParam();

}
