package com.yoti.api.client.spi.remote.call;

public final class YotiConstants {

    private YotiConstants() {}

    public static final String PROPERTY_YOTI_API_URL = "yoti.api.url";

    public static final String DEFAULT_YOTI_HOST = "https://api.yoti.com";
    public static final String YOTI_API_PATH_PREFIX = "/api/v1";
    public static final String DEFAULT_YOTI_API_URL = DEFAULT_YOTI_HOST + YOTI_API_PATH_PREFIX;

    public static final String AUTH_KEY_HEADER = "X-Yoti-Auth-Key";
    public static final String DIGEST_HEADER = "X-Yoti-Auth-Digest";
    public static final String YOTI_SDK_HEADER = "X-Yoti-SDK";
    public static final String YOTI_SDK_VERSION_HEADER = YOTI_SDK_HEADER + "-Version";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String JAVA = "Java";
    public static final String SDK_VERSION = JAVA + "-2.6.0-SNAPSHOT";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ASYMMETRIC_CIPHER = "RSA/NONE/PKCS1Padding";
    public static final String SYMMETRIC_CIPHER = "AES/CBC/PKCS7Padding";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String RFC3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String RFC3339_PATTERN_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

}
