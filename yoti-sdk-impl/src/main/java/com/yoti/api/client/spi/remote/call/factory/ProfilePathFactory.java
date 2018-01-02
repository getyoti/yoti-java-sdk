package com.yoti.api.client.spi.remote.call.factory;

import java.util.UUID;

public class ProfilePathFactory {

    private static final String PATH_TEMPLATE = "/profile/{}?nonce={}&timestamp={}&appId={}";
    private static final String PARAM_PLACEHOLDER = "\\{\\}";

    public String create(String appId, String connectToken) {
        String template = PATH_TEMPLATE.replaceFirst(PARAM_PLACEHOLDER, connectToken);
        template = template.replaceFirst(PARAM_PLACEHOLDER, UUID.randomUUID().toString());
        template = template.replaceFirst(PARAM_PLACEHOLDER, "" + System.nanoTime() / 1000);
        return template.replaceFirst(PARAM_PLACEHOLDER, appId);
    }

}
