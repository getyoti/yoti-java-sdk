package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.String.format;

public class UnsignedPathFactory {

    static final String PROFILE_PATH_TEMPLATE = "/profile/%s?appId=%s";
    static final String AML_PATH_TEMPLATE = "/aml-check?appId=%s";
    static final String QR_CODE_PATH_TEMPLATE = "/qrcodes/apps/%s";

    public String createProfilePath(String appId, String connectToken) {
        return format(PROFILE_PATH_TEMPLATE, connectToken, appId);
    }

    public String createAmlPath(String appId) {
        return format(AML_PATH_TEMPLATE, appId);
    }

    public String createDynamicSharingPath(String appId) {
        return format(QR_CODE_PATH_TEMPLATE, appId);
    }

}
