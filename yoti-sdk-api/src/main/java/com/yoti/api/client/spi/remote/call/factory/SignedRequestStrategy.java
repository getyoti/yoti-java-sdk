package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.spi.remote.call.YotiConstants;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class SignedRequestStrategy implements AuthStrategy {

    private static final SignedMessageFactory signedMessageFactory;

    static {
        signedMessageFactory = SignedMessageFactory.newInstance();
    }

    private final KeyPair keyPair;
    private final String sdkId;

    public SignedRequestStrategy(KeyPair keyPair, String sdkId) {
        this.keyPair = keyPair;
        this.sdkId = sdkId;
    }

    @Override
    public Header createAuthHeader(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException {
        String digest;
        if (payload == null) {
            digest = signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint);
        } else {
            digest = signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint, payload);
        }
        return new BasicHeader(YotiConstants.DIGEST_HEADER, digest);
    }

    @Override
    public NameValuePair getQueryParam() {
        return new BasicNameValuePair("sdkId", sdkId);
    }

}
