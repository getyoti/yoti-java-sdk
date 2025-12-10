package com.yoti.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

import com.yoti.api.client.identity.MatchRequest;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.call.identity.DigitalIdentityException;
import com.yoti.api.client.spi.remote.call.identity.DigitalIdentityService;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.bouncycastle.openssl.PEMException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DigitalIdentityClientTest {

    private static final String AN_SDK_ID = "anSdkId";
    private static final String A_QR_CODE_ID = "aQrCodeId";
    private static final String A_SESSION_ID = "aSessionId";
    private static final String A_RECEIPT_ID = "aReceiptId";

    @InjectMocks DigitalIdentityClient testObj;

    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;
    @Mock DigitalIdentityService identityServiceMock;
    @Mock ShareSessionRequest shareSessionRequest;
    @Mock MatchRequest matchRequest;

    private KeyPairSource validKeyPairSource;

    @Before
    public void setUp() throws Exception {
        validKeyPairSource = new KeyPairSourceTest(CryptoUtil.KEY_PAIR_PEM);
    }

    @Test
    public void builderWithClientSdkId_NullSdkId_IllegalArgumentException() {
        DigitalIdentityClient.Builder builder = DigitalIdentityClient.builder()
                .withKeyPairSource(validKeyPairSource);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> builder.withClientSdkId(null));

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void builderWithClientSdkId_EmptySdkId_IllegalArgumentException() {
        DigitalIdentityClient.Builder builder = DigitalIdentityClient.builder()
                .withKeyPairSource(validKeyPairSource);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> builder.withClientSdkId(""));

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void builderWithKeyPairSource_NullKeyPairSource_IllegalArgumentException() {
        DigitalIdentityClient.Builder builder = DigitalIdentityClient.builder()
                .withClientSdkId(AN_SDK_ID);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> builder.withKeyPairSource(null)
        );

        assertThat(ex.getMessage(), containsString("Key Pair Source"));
    }

    @Test
    public void build_MissingSdkId_IllegalArgumentException() {
        DigitalIdentityClient.Builder builder = DigitalIdentityClient.builder().withKeyPairSource(validKeyPairSource);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, builder::build);

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void build_NoKeyPairInFile_InitialisationException() {
        KeyPairSource invalidKeyPairSource = new KeyPairSourceTest("no-key-pair-in-file");

        DigitalIdentityClient.Builder builder = DigitalIdentityClient.builder()
                .withClientSdkId(AN_SDK_ID)
                .withKeyPairSource(invalidKeyPairSource);

        InitialisationException ex = assertThrows(InitialisationException.class, builder::build);

        assertThat(ex.getMessage(), containsString("No key pair found in the provided source"));
    }

    @Test
    public void build_InvalidKeyPair_InitialisationException() {
        KeyPairSource invalidKeyPairSource = new KeyPairSourceTest(CryptoUtil.INVALID_KEY_PAIR_PEM);

        DigitalIdentityClient.Builder builder = DigitalIdentityClient.builder()
                .withClientSdkId(AN_SDK_ID)
                .withKeyPairSource(invalidKeyPairSource);

        InitialisationException ex = assertThrows(InitialisationException.class, builder::build);

        assertThat(ex.getMessage(), equalTo("Cannot load Key Pair"));
        assertTrue(ex.getCause() instanceof PEMException);
    }

    @Test
    public void client_CreateShareSessionException_DigitalIdentityException() throws IOException {
        String exMessage = "Create Share Session Error";
        when(identityServiceMock.createShareSession(shareSessionRequest))
                .thenThrow(new DigitalIdentityException(exMessage));

        DigitalIdentityException ex = assertThrows(
                DigitalIdentityException.class,
                () -> testObj.createShareSession(shareSessionRequest)
        );

        assertThat(ex.getMessage(), equalTo(exMessage));
    }

    @Test
    public void client_FetchShareSessionException_DigitalIdentityException() throws IOException {
        String exMessage = "Fetch Share Session Error";
        when(identityServiceMock.fetchShareSession(A_SESSION_ID))
                .thenThrow(new DigitalIdentityException(exMessage));

        DigitalIdentityException ex = assertThrows(
                DigitalIdentityException.class,
                () -> testObj.fetchShareSession(A_SESSION_ID)
        );

        assertThat(ex.getMessage(), equalTo(exMessage));
    }

    @Test
    public void client_CreateShareQrCodeException_DigitalIdentityException() throws IOException {
        String exMessage = "Create Share QR Error";
        when(identityServiceMock.createShareQrCode(A_SESSION_ID))
                .thenThrow(new DigitalIdentityException(exMessage));

        DigitalIdentityException ex = assertThrows(
                DigitalIdentityException.class,
                () -> testObj.createShareQrCode(A_SESSION_ID)
        );

        assertThat(ex.getMessage(), equalTo(exMessage));
    }

    @Test
    public void client_FetchShareQrCodeException_DigitalIdentityException() throws IOException {
        String exMessage = "Fetch Share QR Error";
        when(identityServiceMock.fetchShareQrCode(A_QR_CODE_ID))
                .thenThrow(new DigitalIdentityException(exMessage));

        DigitalIdentityException ex = assertThrows(
                DigitalIdentityException.class,
                () -> testObj.fetchShareQrCode(A_QR_CODE_ID)
        );

        assertThat(ex.getMessage(), equalTo(exMessage));
    }

    @Test
    public void client_FetchShareReceiptException_DigitalIdentityException() throws IOException {
        String exMessage = "Fetch Share Receipt Error";
        when(identityServiceMock.fetchShareReceipt(keyPairMock, A_RECEIPT_ID))
                .thenThrow(new DigitalIdentityException(exMessage));

        DigitalIdentityException ex = assertThrows(
                DigitalIdentityException.class,
                () -> testObj.fetchShareReceipt(A_RECEIPT_ID)
        );

        assertThat(ex.getMessage(), equalTo(exMessage));
    }

    @Test
    public void client_FetchDigitalIdMatchException_DigitalIdentityException() throws IOException {
        String exMessage = "Fetch digital identity match error";
        when(identityServiceMock.fetchMatch(matchRequest))
                .thenThrow(new DigitalIdentityException(exMessage));

        DigitalIdentityException ex = assertThrows(
                DigitalIdentityException.class,
                () -> testObj.fetchMatch(matchRequest)
        );

        assertThat(ex.getMessage(), equalTo(exMessage));
    }

    private static class KeyPairSourceTest implements KeyPairSource {

        private final String keyPair;

        public KeyPairSourceTest(String keyPair) {
            this.keyPair = keyPair;
        }

        @Override
        public KeyPair getFromStream(StreamVisitor streamVisitor) throws IOException, InitialisationException {
            InputStream is = new ByteArrayInputStream(keyPair.getBytes());
            return streamVisitor.accept(is);
        }

    }

}
