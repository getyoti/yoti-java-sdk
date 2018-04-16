package com.yoti.api.client.sandbox;

import static com.yoti.api.client.sandbox.validation.Validation.checkNotNullOrEmpty;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.sandbox.profile.SandboxHumanProfile;
import com.yoti.api.client.sandbox.profile.request.HttpUrlConnectionBuilder;
import com.yoti.api.client.sandbox.profile.request.YotiTokenRequest;
import com.yoti.api.client.sandbox.profile.request.YotiTokenRequestMapper;
import com.yoti.api.client.sandbox.profile.response.YotiToken;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;

public class YotiSandboxClient {

    private static final String X_YOTI_AUTH_DIGEST = "X-Yoti-Auth-Digest";
    private static final String SANDBOX_API_URL_SYSTEM_PROPERTY = "yoti.api.url";
    private static final String DEFAULT_SANDBOX_API_URL = "https://api.yoti.com/sanbdox/v1";
    private static final String CREATE_SHARING_TOKEN_PATH = "/apps/%s/tokens";

    private String appId;
    private KeyPair keyPair;
    private String sandboxBasePath;
    private ObjectMapper mapper;
    private SignedMessageFactory signedMessageFactory = SignedMessageFactory.newInstance();

    YotiSandboxClient(String appId, KeyPair keyPair, ObjectMapper mapper) {
        this.appId = appId;
        this.keyPair = keyPair;
        this.mapper = mapper;
        this.sandboxBasePath = System.getProperty(SANDBOX_API_URL_SYSTEM_PROPERTY, DEFAULT_SANDBOX_API_URL);
    }

    public String createActivityDetails(String userId, SandboxHumanProfile sandboxHumanProfile)
            throws ProfileException, IOException {

        checkNotNullOrEmpty(sandboxBasePath, "Sandbox base path");

        String requestPath = String.format(CREATE_SHARING_TOKEN_PATH, appId);
        requestPath = requestPath + addRandomnessToPath(requestPath);

        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, userId);

        String requestDigest;
        try {
            requestDigest = signedMessageFactory.create(keyPair.getPrivate(),"POST", requestPath, mapper.writeValueAsString(
                    yotiTokenRequest).getBytes());
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Cannot sign request", gse);
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(X_YOTI_AUTH_DIGEST, requestDigest);
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpURLConnection connection = HttpUrlConnectionBuilder.newBuilder()
                .schemeAndAuthority(sandboxBasePath)
                .path(requestPath)
                .methodName("POST")
                .headers(headers)
                .build();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);

        postRequestBody(connection, yotiTokenRequest);

        return readResponse(connection);
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder responseBody = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            responseBody.append(inputLine);
        }

        bufferedReader.close();
        return mapper.readValue(responseBody.toString(), YotiToken.class).getToken();
    }

    private void postRequestBody(HttpURLConnection connection, YotiTokenRequest yotiTokenRequest)
            throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(mapper.writeValueAsString(yotiTokenRequest).getBytes());
    }

    private static String addRandomnessToPath(String path) {
        String result;
        if (path.contains("?")) {
            result = "&" + generateRandomness();
        } else {
            result = "?" + generateRandomness();
        }
        return result;
    }

    private static String generateRandomness() {
        return "timestamp=" + System.currentTimeMillis() + "&nonce=" + UUID.randomUUID();
    }

}
