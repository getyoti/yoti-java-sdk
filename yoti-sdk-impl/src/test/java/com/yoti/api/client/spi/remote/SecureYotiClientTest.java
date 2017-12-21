package com.yoti.api.client.spi.remote;

import com.google.protobuf.ByteString;
import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.proto.AttrProto.Attribute;
import com.yoti.api.client.spi.remote.proto.AttributeListProto.AttributeList;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto.ContentType;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto;
import com.yoti.api.client.spi.remote.util.CryptoUtil;
import com.yoti.api.client.spi.remote.util.CryptoUtil.EncryptionResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptSymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKey;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SecureYotiClientTest {

    private static final Map<String, String> PROFILE_ATTRIBUTES;
    private static final Map<String, String> EMPTY_PROFILE = new HashMap<String, String>();

    static {
        PROFILE_ATTRIBUTES = new HashMap<String, String>();
        PROFILE_ATTRIBUTES.put("test-attr1", "test-value2");
        PROFILE_ATTRIBUTES.put("test-attr2", "test-value3");
    }

    private static final String TOKEN = "test-token-test-test-test";
    private static final String APP_ID = "appId";
    private static final byte[] USER_ID = "YmFkYWRhZGEtZGFkYWJhZGEK".getBytes();
    private static final String TIMESTAMP = "2006-01-02T15:04:05Z07:00";
    private static final String INVALID_TIMESTAMP = "xx2006-01-02T15:04:05Z07:00";
    private static final byte[] RECEIPT_ID = { 1, 2, 3, 4, 5, 6, 7, 8 };

    @Mock ProfileService profileServiceMock;

    String encryptedToken;
    KeyPairSource validKeyPairSource;
    byte[] validReceiptKey;
    EncryptionResult encryptionResult;

    @Before
    public void setUp() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();
        encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));
        validKeyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
        validReceiptKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        encryptionResult = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
    }

    @Test
    public void getActivityDetails_shouldGetCorrectProfile() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder()
                .withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .withProfile(profileContent)
                .withTimestamp(TIMESTAMP)
                .withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        assertNotNull(result);
        assertProfileAttributes(result.getUserProfile(), PROFILE_ATTRIBUTES);
    }

    @Test
    public void getActivityDetails_shouldGetEmptyProfileWithNullContent() throws Exception {
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(null)
                .withProfile(null)
                .withTimestamp(TIMESTAMP)
                .withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        assertNotNull(result);
        assertProfileAttributes(result.getUserProfile(), EMPTY_PROFILE);
    }

    @Test
    public void getActivityDetails_shouldGetEmptyProfileWithEmptyContent() throws Exception {
        byte[] profileContent = new byte[0];
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .withProfile(profileContent)
                .withTimestamp(TIMESTAMP)
                .withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        assertNotNull(result);
        assertProfileAttributes(result.getUserProfile(), EMPTY_PROFILE);
    }
    
    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidTimestamp() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .withProfile(profileContent)
                .withTimestamp(INVALID_TIMESTAMP)
                .withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoIV() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, null);
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidIV() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, new byte[] { 1, 2 });
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoReceipt() throws Exception {
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(null);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ActivityFailureException.class)
    public void getActivityDetails_shouldFailWithFailureReceipt() throws Exception {
        Receipt receipt = new Receipt.Builder().withOutcome(Receipt.Outcome.FAILURE).build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidProfileData() throws Exception {
        byte[] profileContent = marshalProfile(new byte[] { 1, 2 }, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidReceiptKey() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, encryptionResult.iv);
        byte[] invalidReceiptKey = { 1, 2, 3 };
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(invalidReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoReceiptKey() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(null)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoProfileData() throws Exception {
        byte[] profileContent = marshalProfile(null, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithExceptionFromProfileService() throws Exception {
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenThrow(new ProfileException("Test exception"));

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWhenStreamExceptionLoadingKeys() throws Exception {
        KeyPairSource exceptionKeyPairSource = new StaticKeyPairSource(true);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, exceptionKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWhenKeyPairSourceExceptionLoadingKeys() throws Exception {
        KeyPairSource exceptionKeyPairSource = new StaticKeyPairSource(false);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, exceptionKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullApplicationId() throws Exception {
        new SecureYotiClient(null, validKeyPairSource, profileServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullKeyPairSource() throws Exception {
        new SecureYotiClient(APP_ID, null, profileServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullProfileService() throws Exception {
        new SecureYotiClient(APP_ID, validKeyPairSource, null);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWithNoKeyPair() throws Exception {
        KeyPairSource invalidKeyPairSource = new StaticKeyPairSource("no-key-pair-in-file");

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, invalidKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWithInvalidKeyPair() throws Exception {
        KeyPairSource invalidKeyPairSource = new StaticKeyPairSource(CryptoUtil.INVALID_KEY_PAIR_PEM);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, invalidKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidToken() throws Exception {
        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(TOKEN);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNullToken() throws Exception {
        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock);
        testObj.getActivityDetails(null);
    }

    private void assertProfileAttributes(Profile profile, Map<String, String> profileAttributes) {
        assertNotNull(profile.getAttributes());
        assertEquals(profileAttributes.size(), profile.getAttributes().size());
        for (Map.Entry<String, String> attribute : profileAttributes.entrySet()) {
            assertEquals(attribute.getValue(), profile.getAttribute(attribute.getKey()));
        }
    }

    private byte[] createProfileData(Map<String, String> testAttributes) {
        AttributeList.Builder attrListBuilder = AttributeList.newBuilder();
        for (Map.Entry<String, String> e : testAttributes.entrySet()) {
            Attribute attribute = Attribute.newBuilder().setContentType(ContentType.STRING).setName(e.getKey())
                    .setValue(ByteString.copyFromUtf8(e.getValue())).build();
            attrListBuilder.addAttributes(attribute);
        }

        return attrListBuilder.build().toByteArray();
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
                throw new IOException("Test exception");
            }
            if (streamException) {
                InputStream is = mock(InputStream.class);
                when(is.read()).thenThrow(new IOException("Test exception"));
                return streamVisitor.accept(is);
            } else {
                InputStream is = new ByteArrayInputStream(keypair.getBytes());
                return streamVisitor.accept(is);
            }
        }
    }

    private byte[] marshalProfile(byte[] data, byte[] iv) {
        EncryptedDataProto.EncryptedData.Builder eb = EncryptedDataProto.EncryptedData.newBuilder();
        if (iv != null) {
            eb = eb.setIv(ByteString.copyFrom(iv));
        }
        if (data != null) {
            eb = eb.setCipherText(ByteString.copyFrom(data));
        }
        return eb.build().toByteArray();
    }

}
