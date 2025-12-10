package com.yoti.auth;

/**
 * Internal use only.
 * <p>
 * Contains property values used by the `yoti-sdk-auth` module for
 * creating Yoti Authentication tokens.
 */
final class Properties {

    private static final String YOTI_AUTH_HOST = "https://auth.api.yoti.com";
    private static final String YOTI_AUTH_PATH_PREFIX = "/v1/oauth/token";

    public static final String PROPERTY_YOTI_AUTH_URL = "yoti.auth.url";
    public static final String DEFAULT_YOTI_AUTH_URL = YOTI_AUTH_HOST + YOTI_AUTH_PATH_PREFIX;

}
