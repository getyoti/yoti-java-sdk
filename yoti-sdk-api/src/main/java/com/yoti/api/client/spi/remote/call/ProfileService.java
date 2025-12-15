package com.yoti.api.client.spi.remote.call;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_KEY_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;
import java.util.Base64;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.ProfileSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileService {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);

    private final UnsignedPathFactory unsignedPathFactory;
    private final YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory;
    private final String apiUrl;
    private final ProfileSignedRequestStrategy profileSignedRequestStrategy;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static ProfileService newInstance(KeyPair keyPair, String appId) {
        return new ProfileService(
                new UnsignedPathFactory(),
                new YotiHttpRequestBuilderFactory(),
                new ProfileSignedRequestStrategy(keyPair, appId));
    }

    private ProfileService(UnsignedPathFactory unsignedPathFactory,
            YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory,
            ProfileSignedRequestStrategy profileSignedRequestStrategy) {
        this.unsignedPathFactory = unsignedPathFactory;
        this.yotiHttpRequestBuilderFactory = yotiHttpRequestBuilderFactory;
        this.profileSignedRequestStrategy = profileSignedRequestStrategy;
        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public Receipt getReceipt(KeyPair keyPair, String connectToken) throws ProfileException {
        return getProfile(keyPair, connectToken).getReceipt();
    }

    public ProfileResponse getProfile(KeyPair keyPair, String connectToken) throws ProfileException {
        notNull(keyPair, "Key pair");
        notNull(connectToken, "Connect token");

        String path = unsignedPathFactory.createProfilePath(connectToken);

        try {
            String authKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            YotiHttpRequest yotiHttpRequest = createRequest(path, authKey);
            return fetchReceipt(yotiHttpRequest);
        } catch (IOException ioe) {
            throw new ProfileException("Error calling service to get profile", ioe);
        }
    }

    private ProfileResponse fetchReceipt(YotiHttpRequest yotiHttpRequest) throws IOException, ProfileException {
        LOG.info("Fetching profile from resource at '{}'", yotiHttpRequest.getUri());

        try {
            return yotiHttpRequest.execute(ProfileResponse.class);
        } catch (ResourceException ex) {
            int responseCode = ex.getResponseCode();
            switch (responseCode) {
                case HTTP_INTERNAL_ERROR:
                    throw new ProfileException("Error completing sharing: " + ex.getResponseBody(), ex);
                case HTTP_NOT_FOUND:
                    throw new ProfileException("Profile not found. This can be due to a used or expired token.", ex);
                default:
                    throw new ProfileException("Unexpected response: " + responseCode + " " + ex.getResponseBody(), ex);
            }
        }
    }

    YotiHttpRequest createRequest(String path, String authKey) throws ProfileException {
        try {
            return yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(profileSignedRequestStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .withHeader(AUTH_KEY_HEADER, authKey)
                    .build();
        } catch (GeneralSecurityException ex) {
            throw new ProfileException("Cannot sign request", ex);
        } catch (UnsupportedEncodingException | URISyntaxException ex) {
            throw new ProfileException("Error creating request", ex);
        }
    }
    
}
