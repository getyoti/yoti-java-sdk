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
import com.yoti.api.client.spi.remote.call.factory.SignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileService {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);

    private final UnsignedPathFactory unsignedPathFactory;
    private final YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory;
    private final String apiUrl;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static ProfileService newInstance() {
        return new ProfileService(
                new UnsignedPathFactory(),
                new YotiHttpRequestBuilderFactory());
    }

    ProfileService(UnsignedPathFactory profilePathFactory, YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory) {
        this.unsignedPathFactory = profilePathFactory;
        this.yotiHttpRequestBuilderFactory = yotiHttpRequestBuilderFactory;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public Receipt getReceipt(SignedRequestStrategy signedRequestThingy, KeyPair keyPair, String appId, String connectToken) throws ProfileException {
        return getProfile(signedRequestThingy, keyPair, appId, connectToken).getReceipt();
    }

    public ProfileResponse getProfile(SignedRequestStrategy signedRequestThingy, KeyPair keyPair, String appId, String connectToken) throws ProfileException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(connectToken, "Connect token");

        String path = unsignedPathFactory.createProfilePath(appId, connectToken);

        try {
            String authKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            YotiHttpRequest yotiHttpRequest = createSignedRequest(signedRequestThingy, path, authKey);
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

    YotiHttpRequest createSignedRequest(SignedRequestStrategy signedRequestThingy, String path, String authKey) throws ProfileException {
        try {
            return yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(signedRequestThingy)
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
