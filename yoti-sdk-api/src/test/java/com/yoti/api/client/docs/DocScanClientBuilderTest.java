package com.yoti.api.client.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.common.StaticKeyPairSource;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.junit.Before;
import org.junit.Test;

public class DocScanClientBuilderTest {

    private static final String SOME_APPLICATION_ID = "someAppId";
    private static final String SOME_AUTH_TOKEN = "someAuthToken";

    private KeyPairSource validKeyPairSource;

    @Before
    public void setUp() {
        validKeyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
    }

    @Test
    public void shouldThrowExceptionWhenSdkIdIsNull() {
        DocScanClient.Builder builder = DocScanClient.builder();

        IllegalStateException ex = assertThrows(IllegalStateException.class, builder::build);

        assertThat(ex.getMessage(), containsString("sdkId"));
    }

    @Test
    public void shouldThrowExceptionWhenSdkIdIsEmpty() {
        DocScanClient.Builder builder = DocScanClient.builder()
                .withClientSdkId("");

        IllegalStateException ex = assertThrows(IllegalStateException.class, builder::build);

        assertThat(ex.getMessage(), containsString("sdkId"));
    }

    @Test
    public void shouldThrowExceptionWhenKeyPairSourceIsNull() {
        DocScanClient.Builder builder = DocScanClient.builder()
                .withClientSdkId(SOME_APPLICATION_ID);

        IllegalStateException ex = assertThrows(IllegalStateException.class, builder::build);

        assertThat(ex.getMessage(), containsString("KeyPairSource"));
    }

    @Test
    public void shouldThrowExceptionWhenSdkIdIsGivenAlongWithAuthToken() {
        DocScanClient.Builder builder = DocScanClient.builder()
                .withClientSdkId(SOME_APPLICATION_ID)
                .withAuthenticationToken(SOME_AUTH_TOKEN);

        IllegalStateException ex = assertThrows(IllegalStateException.class, builder::build);

        assertThat(ex.getMessage(), containsString("sdkId or KeyPairSource"));
    }

    @Test
    public void shouldThrowExceptionWhenKeyPairSourceIsGivenAlongWithAuthToken() {
        DocScanClient.Builder builder = DocScanClient.builder()
                .withKeyPairSource(validKeyPairSource)
                .withAuthenticationToken(SOME_AUTH_TOKEN);

        IllegalStateException ex = assertThrows(IllegalStateException.class, builder::build);

        assertThat(ex.getMessage(), containsString("sdkId or KeyPairSource"));
    }

    @Test
    public void shouldFailWhenStreamExceptionLoadingKeys() {
        KeyPairSource badKeyPairSource = new StaticKeyPairSource(true);
        DocScanClient.Builder builder = DocScanClient.builder()
                .withClientSdkId(SOME_APPLICATION_ID)
                .withKeyPairSource(badKeyPairSource);

        InitialisationException ex = assertThrows(InitialisationException.class, builder::build);

        IOException ioException = (IOException) ex.getCause();
        assertThat(ioException.getMessage(), containsString("Test stream exception"));
    }

    @Test
    public void shouldCorrectlyBuildDocScanClientForUsingSignedRequests() {
        DocScanClient result = DocScanClient.builder()
                .withClientSdkId(SOME_APPLICATION_ID)
                .withKeyPairSource(validKeyPairSource)
                .build();

        assertThat(result, is(notNullValue()));
    }

    @Test
    public void shouldCorrectlyBuildDocScanClientForUsingAuthTokens() {
        DocScanClient result = DocScanClient.builder()
                .withAuthenticationToken(SOME_AUTH_TOKEN)
                .build();

        assertThat(result, is(notNullValue()));
    }

}
