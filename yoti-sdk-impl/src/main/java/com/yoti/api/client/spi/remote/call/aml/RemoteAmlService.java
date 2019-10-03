package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.*;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static java.net.HttpURLConnection.*;

public class RemoteAmlService {

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final SignedRequestBuilder signedRequestBuilder;
    private final String apiUrl;

    public static RemoteAmlService newInstance() {
        return new RemoteAmlService(
                new UnsignedPathFactory(),
                new ObjectMapper(),
                SignedRequestBuilder.newInstance()
        );
    }

    RemoteAmlService(UnsignedPathFactory unsignedPathFactory,
                     ObjectMapper objectMapper,
                     SignedRequestBuilder signedRequestBuilder) {
        this.unsignedPathFactory = unsignedPathFactory;
        this.objectMapper = objectMapper;
        this.signedRequestBuilder = signedRequestBuilder;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public SimpleAmlResult performCheck(KeyPair keyPair, String appId, AmlProfile amlProfile) throws AmlException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(amlProfile, "amlProfile");

        try {
            String resourcePath = unsignedPathFactory.createAmlPath(appId);
            byte[] body = objectMapper.writeValueAsString(amlProfile).getBytes(DEFAULT_CHARSET);

            SignedRequest signedRequest = this.signedRequestBuilder.withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(resourcePath)
                    .withPayload(body)
                    .withHttpMethod(HTTP_POST)
                    .build();

            return signedRequest.execute(SimpleAmlResult.class);
        } catch (IOException ioException) {
            throw new AmlException("Error communicating with AML endpoint", ioException);
        } catch (GeneralSecurityException generalSecurityException) {
            throw new AmlException("Cannot sign request", generalSecurityException);
        } catch (URISyntaxException uriSyntaxException) {
            throw new AmlException("Error creating request", uriSyntaxException);
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

}
