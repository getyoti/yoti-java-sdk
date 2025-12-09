package com.yoti.api.client;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

import com.yoti.api.client.spi.remote.ActivityDetailsFactory;
import com.yoti.api.client.spi.remote.ReceiptFetcher;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;
import com.yoti.api.client.spi.remote.call.qrcode.DynamicSharingService;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.bouncycastle.openssl.PEMException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class YotiClientTest {

    private static final String TOKEN = "test-token-test-test-test";
    private static final String APP_ID = "appId";

    @Mock ReceiptFetcher receiptFetcherMock;
    @Mock RemoteAmlService remoteAmlServiceMock;
    @Mock ActivityDetailsFactory activityDetailsFactoryMock;
    @Mock DynamicSharingService sharingServiceMock;

    @Mock Receipt receiptMock;
    @Mock ActivityDetails activityDetailsMock;
    String encryptedToken;
    @Mock KeyPair keyPairMock;

    @Before
    public void setUp() throws Exception {
        KeyPair keyPair = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM);
        encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), keyPair.getPublic()));
    }

    @Test
    public void getActivityDetails_shouldFailWithExceptionFromReceiptFetcher() throws Exception {
        ProfileException profileException = new ProfileException("Test exception");
        when(receiptFetcherMock.fetch(eq(encryptedToken), any(KeyPair.class))).thenThrow(profileException);

        try {
            YotiClient testObj = new YotiClient(APP_ID, keyPairMock, receiptFetcherMock, remoteAmlServiceMock, activityDetailsFactoryMock, sharingServiceMock);
            testObj.getActivityDetails(encryptedToken);
        } catch (ProfileException ex) {
            assertSame(profileException, ex);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void getActivityDetails_shouldFailWithExceptionFromActivityDetailsFactory() throws Exception {
        ProfileException profileException = new ProfileException("Test exception");
        when(receiptFetcherMock.fetch(encryptedToken, keyPairMock)).thenReturn(receiptMock);
        when(activityDetailsFactoryMock.create(receiptMock, keyPairMock.getPrivate())).thenThrow(profileException);

        YotiClient testObj = new YotiClient(APP_ID, keyPairMock, receiptFetcherMock, remoteAmlServiceMock, activityDetailsFactoryMock, sharingServiceMock);
        ProfileException thrown = assertThrows(ProfileException.class, () -> testObj.getActivityDetails(encryptedToken));

        assertSame(profileException, thrown);
        verify(activityDetailsFactoryMock).create(receiptMock, keyPairMock.getPrivate());
    }

    @Test
    public void getActivityDetails_shouldReturnActivityDetails() throws Exception {
        when(receiptFetcherMock.fetch(encryptedToken, keyPairMock)).thenReturn(receiptMock);
        when(activityDetailsFactoryMock.create(receiptMock, keyPairMock.getPrivate())).thenReturn(activityDetailsMock);

        YotiClient testObj = new YotiClient(APP_ID, keyPairMock, receiptFetcherMock, remoteAmlServiceMock, activityDetailsFactoryMock, sharingServiceMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        assertSame(activityDetailsMock, result);
    }

    @Test
    public void builder_shouldFailWhenStreamExceptionLoadingKeys() {
        KeyPairSource badKeyPairSource = new StaticKeyPairSource(true);
        YotiClient.Builder builder = YotiClient.builder()
                .withClientSdkId(APP_ID)
                .withKeyPair(badKeyPairSource);

        InitialisationException thrown = assertThrows(InitialisationException.class, builder::build);

        assertTrue(thrown.getCause() instanceof IOException);
        assertThat(thrown.getCause().getMessage(), containsString("Test stream exception"));
    }

    @Test
    public void builder_shouldFailWhenKeyPairSourceExceptionLoadingKeys() {
        KeyPairSource badKeyPairSource = new StaticKeyPairSource(false);
        YotiClient.Builder builder = YotiClient.builder()
                .withClientSdkId(APP_ID)
                .withKeyPair(badKeyPairSource);

        InitialisationException thrown = assertThrows(InitialisationException.class, builder::build);

        assertTrue(thrown.getCause() instanceof IOException);
        assertThat(thrown.getCause().getMessage(), containsString("Test source exception"));
    }

    @Test
    public void constructor_shouldFailWithNullApplicationId() {
        try {
            new YotiClient(null, null, null, null, null, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void constructor_shouldFailWithNullReceiptFetcher() {
        try {
            new YotiClient(APP_ID, keyPairMock, null, null, null, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("receiptFetcher"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void constructor_shouldFailWithNullActivityDetailsFactory() {
        try {
            new YotiClient(APP_ID, keyPairMock, receiptFetcherMock, remoteAmlServiceMock, null, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("activityDetailsFactory"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void constructor_shouldFailWithNullAmlService() {
        try {
            new YotiClient(APP_ID, keyPairMock, receiptFetcherMock, null, null, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("amlService"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldFailWithNoKeyPair() {
        KeyPairSource invalidKeyPairSource = new StaticKeyPairSource("no-key-pair-in-file");
        YotiClient.Builder builder = YotiClient.builder()
                .withClientSdkId(APP_ID)
                .withKeyPair(invalidKeyPairSource);

        InitialisationException thrown = assertThrows(InitialisationException.class, builder::build);

        assertThat(thrown.getMessage(), containsString("No key pair found in the provided source"));
    }

    @Test
    public void builder_shouldFailWithInvalidKeyPair() {
        KeyPairSource invalidKeyPairSource = new StaticKeyPairSource(CryptoUtil.INVALID_KEY_PAIR_PEM);
        YotiClient.Builder builder = YotiClient.builder()
                .withClientSdkId(APP_ID)
                .withKeyPair(invalidKeyPairSource);

        InitialisationException thrown = assertThrows(InitialisationException.class, builder::build);

        assertThat(thrown.getMessage(), containsString("Cannot load key pair"));
        assertTrue(thrown.getCause() instanceof PEMException);
    }

    private static class StaticKeyPairSource implements KeyPairSource {

        private String keypair;
        private boolean streamException;
        private boolean sourceException;

        public StaticKeyPairSource(boolean inStreamException) {
            this.streamException = inStreamException;
            this.sourceException = !inStreamException;
        }

        public StaticKeyPairSource(String keypair) {
            this.keypair = keypair;
        }

        @Override
        public KeyPair getFromStream(StreamVisitor streamVisitor) throws IOException, InitialisationException {
            if (sourceException) {
                throw new IOException("Test source exception");
            }
            if (streamException) {
                InputStream inputStreamMock = mock(InputStream.class);
                when(inputStreamMock.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException("Test stream exception"));
                return streamVisitor.accept(inputStreamMock);
            } else {
                InputStream is = new ByteArrayInputStream(keypair.getBytes());
                return streamVisitor.accept(is);
            }
        }

    }

}
