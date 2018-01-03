package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.Base64;
import com.yoti.api.client.spi.remote.call.factory.ProfilePathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignatureFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public final class RemoteProfileService implements ProfileService {

    public static final String YOTI_API_PATH_PREFIX = "/api/v1";

    private static final Logger LOG = LoggerFactory.getLogger(RemoteProfileService.class);

    private static final String PROPERTY_YOTI_API_URL = "yoti.api.url";
    private static final String DEFAULT_YOTI_HOST = "https://api.yoti.com";
    public static final String DEFAULT_YOTI_API_URL = DEFAULT_YOTI_HOST + YOTI_API_PATH_PREFIX;
    private static final String MESSAGE_PREFIX = "GET&";

    private static final String AUTH_KEY_HEADER = "X-Yoti-Auth-Key";
    private static final String DIGEST_HEADER = "X-Yoti-Auth-Digest";
    private static final String YOTI_SDK_HEADER = "X-Yoti-SDK";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String STRING_ENCODING = "UTF-8";

    private final ResourceFetcher resourceFetcher;
    private final ProfilePathFactory profilePathFactory;
    private final SignatureFactory signatureFactory;
    private final String apiUrl;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static RemoteProfileService newInstance() {
        return new RemoteProfileService(JsonResourceFetcher.createInstance(), new ProfilePathFactory(), new SignatureFactory());
    }

    RemoteProfileService(ResourceFetcher resourceFetcher,
                         ProfilePathFactory profilePathFactory,
                         SignatureFactory signatureFactory) {
        this.resourceFetcher = resourceFetcher;
        this.profilePathFactory = profilePathFactory;
        this.signatureFactory = signatureFactory;
        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    @Override
    public Receipt getReceipt(KeyPair keyPair, String appId, String connectToken) throws ProfileException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(connectToken, "Connect token");

        String path = profilePathFactory.create(appId, connectToken);
        String digest = base64(signatureFactory.create(assembleMessage(path), keyPair.getPrivate()));
        String authKey = base64(keyPair.getPublic().getEncoded());

        try {
            return fetchReceipt(path, digest, authKey);
        } catch (IOException ioe) {
            throw new ProfileException("Error calling service to get profile", ioe);
        }
    }

    private byte[] assembleMessage(String path) {
        try {
            return (MESSAGE_PREFIX + path).getBytes(STRING_ENCODING);
        } catch (UnsupportedEncodingException e) {
            // should not happen. JVM spec mandates UTF-8 support.
            throw new RuntimeException("Unable to convert string to bytes", e);
        }
    }

    private Receipt fetchReceipt(String resourcePath, String digest, String authKey) throws IOException, ProfileException {
        LOG.info("Fetching profile from resource at {}", resourcePath);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTH_KEY_HEADER, authKey);
        headers.put(DIGEST_HEADER, digest);
        headers.put(YOTI_SDK_HEADER, "Java");
        headers.put(CONTENT_TYPE, "application/json");

        UrlConnector urlConnector = UrlConnector.get(apiUrl + resourcePath);

        try {
            ProfileResponse response = resourceFetcher.fetchResource(urlConnector, headers, ProfileResponse.class);
            return response.getReceipt();
        } catch (ResourceException re) {
            int responseCode = re.getResponseCode();
            switch (responseCode) {
                case HTTP_INTERNAL_ERROR:
                    throw new ProfileException("Error completing sharing: " + re.getResponseBody(), re);
                case HTTP_NOT_FOUND:
                    throw new ProfileException("Profile not found. This can be due to a used or expired token. Details: "
                            + re.getResponseBody(), re);
                default:
                    throw new ProfileException("Unexpected response: " + responseCode + " " + re.getResponseBody(), re);
            }
        }
    }

    private static String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

}
