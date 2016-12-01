package com.yoti.api.client.spi.remote.call;

import java.security.KeyPair;

import com.yoti.api.client.ProfileException;

public interface ProfileService {
    Receipt getReceipt(KeyPair keyPair, String appId, String connectToken) throws ProfileException;
}
