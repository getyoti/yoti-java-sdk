package com.yoti.api.client.sandbox;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

import com.yoti.api.client.spi.remote.call.factory.PathFactory;

public class SandboxPathFactory extends PathFactory {

    private static final String CREATE_SANDBOX_TOKEN_PATH = "/apps/%s/tokens?timestamp=%s&nonce=%s";

    public String createSandboxPath(String appId) {
        return format(CREATE_SANDBOX_TOKEN_PATH, appId, createTimestamp(), randomUUID());
    }

}
