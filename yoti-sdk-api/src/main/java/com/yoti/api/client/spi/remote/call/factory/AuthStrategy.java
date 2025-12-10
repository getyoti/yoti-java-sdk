package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

public interface AuthStrategy {

    List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException;

    List<NameValuePair> createQueryParams();

}
