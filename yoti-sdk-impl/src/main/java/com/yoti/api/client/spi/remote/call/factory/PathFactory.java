package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.String.format;
import static java.lang.System.nanoTime;
import static java.util.UUID.randomUUID;

public class PathFactory {

    private static final String PROFILE_PATH_TEMPLATE = "/profile/%s?nonce=%s&timestamp=%s&appId=%s";
    private static final String AML_PATH_TEMPLATE = "/aml-check?appId=%s&nonce=%s&timestamp=%s";

    public String createProfilePath(String appId, String connectToken) {
        return format(PROFILE_PATH_TEMPLATE, connectToken, randomUUID(), createTimestamp(), appId);
    }

    public String createAmlPath(String appId) {
        return format(AML_PATH_TEMPLATE, appId, randomUUID(), createTimestamp());
    }

    protected long createTimestamp() {
        return nanoTime() / 1000;
    }

}
