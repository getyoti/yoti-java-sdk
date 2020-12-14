package com.yoti.api.client.spi.remote.call.aml;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.AmlException;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RemoteAmlService {

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final String apiUrl;

    public static RemoteAmlService newInstance() {
        return new RemoteAmlService(
                new UnsignedPathFactory(),
                new ObjectMapper()
        );
    }

    RemoteAmlService(UnsignedPathFactory unsignedPathFactory, ObjectMapper objectMapper) {
        this.unsignedPathFactory = unsignedPathFactory;
        this.objectMapper = objectMapper;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public SimpleAmlResult performCheck(KeyPair keyPair, String appId, AmlProfile amlProfile) throws AmlException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(amlProfile, "amlProfile");

        try {
            String resourcePath = unsignedPathFactory.createAmlPath(appId);
            byte[] body = objectMapper.writeValueAsString(amlProfile).getBytes(DEFAULT_CHARSET);

            SignedRequest signedRequest = createSignedRequest(keyPair, resourcePath, body);
            return signedRequest.execute(SimpleAmlResult.class);
        } catch (IOException ioException) {
            throw new AmlException("Error communicating with AML endpoint", ioException);
        } catch (ResourceException resourceException) {
            throw createExceptionFromStatusCode(resourceException);
        }
    }

    private AmlException createExceptionFromStatusCode(ResourceException e) {
        switch (e.getResponseCode()) {
            case HTTP_BAD_REQUEST:
                return new AmlException("Failed validation:\n" + e.getResponseBody(), e);
            case HTTP_UNAUTHORIZED:
                return new AmlException("Failed authorization with the given key:\n" + e.getResponseBody(), e);
            case HTTP_INTERNAL_ERROR:
                return new AmlException("An unexpected error occured on the server:\n" + e.getResponseBody(), e);
            default:
                return new AmlException("Unexpected error:\n" + e.getResponseBody(), e);
        }
    }

    SignedRequest createSignedRequest(KeyPair keyPair, String resourcePath, byte[] body) throws AmlException {
        try {
            return SignedRequestBuilder.newInstance()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(resourcePath)
                    .withPayload(body)
                    .withHttpMethod(HTTP_POST)
                    .build();
        } catch (GeneralSecurityException generalSecurityException) {
            throw new AmlException("Cannot sign request", generalSecurityException);
        } catch (URISyntaxException | UnsupportedEncodingException uriSyntaxException) {
            throw new AmlException("Error creating request", uriSyntaxException);
        }
    }

}
