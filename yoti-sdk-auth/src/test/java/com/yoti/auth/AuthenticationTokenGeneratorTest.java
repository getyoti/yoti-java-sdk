package com.yoti.auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.auth.util.CryptoUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationTokenGeneratorTest {

    private static final String SOME_AUTH_API_URL = "https://someAuthApiUrl";
    private static final String SOME_SDK_ID = "someSdkId";
    private static final Supplier<String> SOME_JWT_ID_SUPPLIER = () -> UUID.randomUUID().toString();
    private static final byte[] SOME_RESPONSE_BODY = new byte[] { 1, 2, 3 };

    @Mock FormRequestClient formRequestClientMock;
    @Mock ObjectMapper objectMapperMock;

    @Mock KeyPairSource keyPairSourceMock;
    @Mock CreateAuthenticationTokenResponse createAuthenticationTokenResponseMock;

    @Captor ArgumentCaptor<URL> urlArgumentCaptor;
    @Captor ArgumentCaptor<Map<String, String>> formParamsCaptor;

    private KeyPair validKeyPair;

    @Before
    public void setUp() throws IOException {
        System.setProperty(Properties.PROPERTY_YOTI_AUTH_URL, SOME_AUTH_API_URL);
        validKeyPair = CryptoUtil.generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM);
    }

    @Test
    public void builder_shouldThrowIfSdkIdIsNotProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AuthenticationTokenGenerator.builder().build();
        });

        assertThat(exception.getMessage(), is("'sdkId' must not be empty or null"));
    }

    @Test
    public void builder_shouldThrowIfKeyPairSourceIsNotProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AuthenticationTokenGenerator.builder()
                    .withSdkId(SOME_SDK_ID)
                    .build();
        });

        assertThat(exception.getMessage(), is("'keyPairSource' must not be null"));
    }

    @Test
    public void builder_shouldThrowIfKeyPairSourceIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AuthenticationTokenGenerator.builder()
                    .withSdkId(SOME_SDK_ID)
                    .withKeyPairSource(null)
                    .build();
        });

        assertThat(exception.getMessage(), is("'keyPairSource' must not be null"));
    }

    @Test
    public void builder_shouldThrowIfJwtIdSupplierIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AuthenticationTokenGenerator.builder()
                    .withSdkId(SOME_SDK_ID)
                    .withKeyPairSource(keyPairSourceMock)
                    .withJwtIdSupplier(null)
                    .build();
        });

        assertThat(exception.getMessage(), is("'jwtIdSupplier' must not be null"));
    }

    @Test
    public void shouldJoinScopesCorrectly() throws Exception {
        when(formRequestClientMock.performRequest(urlArgumentCaptor.capture(), eq("POST"), formParamsCaptor.capture())).thenReturn(SOME_RESPONSE_BODY);
        when(objectMapperMock.readValue(SOME_RESPONSE_BODY, CreateAuthenticationTokenResponse.class)).thenReturn(createAuthenticationTokenResponseMock);

        AuthenticationTokenGenerator tokenGenerator = new AuthenticationTokenGenerator(SOME_SDK_ID, validKeyPair, SOME_JWT_ID_SUPPLIER, formRequestClientMock, objectMapperMock);

        List<String> scopes = Arrays.asList("scope1", "scope2", "scope3:read");

        CreateAuthenticationTokenResponse result = tokenGenerator.generate(scopes);

        assertThat(result, is(createAuthenticationTokenResponseMock));

        Map<String, String> formParams = formParamsCaptor.getValue();
        assertThat(formParams.get("grant_type"), is("client_credentials"));
        assertThat(formParams.get("client_assertion_type"), is("urn:ietf:params:oauth:client-assertion-type:jwt-bearer"));
        assertThat(formParams.get("scope"), is("scope1 scope2 scope3:read"));

        assertThat(urlArgumentCaptor.getValue().toString(), is(SOME_AUTH_API_URL));
    }

}
