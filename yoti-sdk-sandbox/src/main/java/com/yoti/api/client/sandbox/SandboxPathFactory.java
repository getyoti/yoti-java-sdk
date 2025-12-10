package com.yoti.api.client.sandbox;

import static java.lang.String.format;

public class SandboxPathFactory {

    private static final String CREATE_SANDBOX_TOKEN_PATH = "/apps/%s/tokens";

    public String createSandboxPath(String appId) {
        return format(CREATE_SANDBOX_TOKEN_PATH, appId);
    }

}
