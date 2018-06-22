package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptSymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKey;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.AttributeListProto.AttributeList;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto;
import com.yoti.api.client.spi.remote.util.CryptoUtil;
import com.yoti.api.client.spi.remote.util.CryptoUtil.EncryptionResult;

import com.google.protobuf.ByteString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SecureYotiClientTest {

    private static final String STRING_ATTRIBUTE_NAME = "testStringAttr";
    private static final String DATE_ATTRIBUTE_NAME = "testDateAttr";
    private static final String JSON_ATTRIBUTE_NAME = "testJsonAttr";
    private static final AttrProto.Attribute STRING_ATTRIBUTE_PROTO = createAttribute(STRING_ATTRIBUTE_NAME);
    private static final AttrProto.Attribute DATE_ATTRIBUTE_PROTO = createAttribute(DATE_ATTRIBUTE_NAME);
    private static final AttrProto.Attribute JSON_ATTRIBUTE_PROTO = createAttribute(JSON_ATTRIBUTE_NAME);

    private static final String TOKEN = "test-token-test-test-test";
    private static final String APP_ID = "appId";
    private static final byte[] USER_ID = "YmFkYWRhZGEtZGFkYWJhZGEK".getBytes();
    private static final String TIMESTAMP = "2006-01-02T15:04:05Z07:00";
    private static final String INVALID_TIMESTAMP = "xx2006-01-02T15:04:05Z07:00";
    private static final byte[] RECEIPT_ID = { 1, 2, 3, 4, 5, 6, 7, 8 };

    @Mock ProfileService profileServiceMock;
    @Mock RemoteAmlService remoteAmlServiceMock;
    @Mock AttributeConverter attributeConverterMock;

    @Mock Attribute<String> stringAttributeMock;
    @Mock Attribute<Date> dateAttributeMock;
    @Mock Attribute<Map> jsonAttributeMock;

    String encryptedToken;
    KeyPairSource validKeyPairSource;
    byte[] validReceiptKey;
    EncryptionResult encryptionResult;

    @Before
    public void setUp() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();
        validReceiptKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        encryptionResult = encryptSymmetric(createProfileData(), receiptKey);

        encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));
        validKeyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        when(stringAttributeMock.getName()).thenReturn(STRING_ATTRIBUTE_NAME);
        when(dateAttributeMock.getName()).thenReturn(DATE_ATTRIBUTE_NAME);
        when(jsonAttributeMock.getName()).thenReturn(JSON_ATTRIBUTE_NAME);
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
        when(attributeConverterMock.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttributeMock);
        when(attributeConverterMock.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenReturn(dateAttributeMock);
        when(attributeConverterMock.<Map>convertAttribute(JSON_ATTRIBUTE_PROTO)).thenReturn(jsonAttributeMock);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        Collection<Attribute<?>> profileAttributes = result.getUserProfile().getAttributes();
        assertThat(profileAttributes, hasSize(3));
        assertThat(profileAttributes, hasItems(stringAttributeMock, dateAttributeMock, jsonAttributeMock));
    }

    @Test
    public void getActivityDetails_shouldTolerateFailureToParseSomeAttributes() throws Exception {
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
        when(attributeConverterMock.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttributeMock);
        when(attributeConverterMock.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenThrow(new IOException());
        when(attributeConverterMock.<Map>convertAttribute(JSON_ATTRIBUTE_PROTO)).thenThrow(new ParseException("some message", 1));

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        Collection<Attribute<?>> profileAttributes = result.getUserProfile().getAttributes();
        assertThat(profileAttributes, hasSize(1));
        assertThat(profileAttributes, hasItem(stringAttributeMock));
    }

    @Test
    public void getActivityDetails_shouldGetEmptyProfileWithNullContent() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(null)
                .withProfile(null)
                .withTimestamp(TIMESTAMP)
                .withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        assertNotNull(result);
        Profile profile = result.getUserProfile();
        assertNotNull(profile.getAttributes());
        assertEquals(0, profile.getAttributes().size());
    }

    @Test
    public void getActivityDetails_shouldGetEmptyProfileWithEmptyContent() throws Exception {
        byte[] profileContent = new byte[0];
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

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        ActivityDetails result = testObj.getActivityDetails(encryptedToken);

        assertNotNull(result);
        Profile profile = result.getUserProfile();
        assertNotNull(profile.getAttributes());
        assertEquals(0, profile.getAttributes().size());
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidTimestamp() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(null)
                .withProfile(null)
                .withTimestamp(INVALID_TIMESTAMP)
                .withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoIV() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, null);
        Receipt receipt = new Receipt.Builder()
                .withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidIV() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, new byte[] { 1, 2 });
        Receipt receipt = new Receipt.Builder()
                .withRememberMeId(USER_ID)
                .withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoReceipt() throws Exception {
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(null);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ActivityFailureException.class)
    public void getActivityDetails_shouldFailWithFailureReceipt() throws Exception {
        Receipt receipt = new Receipt.Builder().withOutcome(Receipt.Outcome.FAILURE).build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidProfileData() throws Exception {
        byte[] profileContent = marshalProfile(new byte[] { 1, 2 }, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
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

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoReceiptKey() throws Exception {
        byte[] profileContent = marshalProfile(encryptionResult.data, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(null)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNoProfileData() throws Exception {
        byte[] profileContent = marshalProfile(null, encryptionResult.iv);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(validReceiptKey)
                .withOtherPartyProfile(profileContent)
                .build();
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(receipt);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithExceptionFromProfileService() throws Exception {
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenThrow(new ProfileException("Test exception"));

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWhenStreamExceptionLoadingKeys() throws Exception {
        KeyPairSource exceptionKeyPairSource = new StaticKeyPairSource(true);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, exceptionKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWhenKeyPairSourceExceptionLoadingKeys() throws Exception {
        KeyPairSource exceptionKeyPairSource = new StaticKeyPairSource(false);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, exceptionKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullApplicationId() throws Exception {
        new SecureYotiClient(null, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullKeyPairSource() throws Exception {
        new SecureYotiClient(APP_ID, null, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullProfileService() throws Exception {
        new SecureYotiClient(APP_ID, validKeyPairSource, null, remoteAmlServiceMock, attributeConverterMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailWithNullAmlService() throws Exception {
        new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, null, attributeConverterMock);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWithNoKeyPair() throws Exception {
        KeyPairSource invalidKeyPairSource = new StaticKeyPairSource("no-key-pair-in-file");

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, invalidKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void getActivityDetails_shouldFailWithInvalidKeyPair() throws Exception {
        KeyPairSource invalidKeyPairSource = new StaticKeyPairSource(CryptoUtil.INVALID_KEY_PAIR_PEM);

        SecureYotiClient testObj = new SecureYotiClient(APP_ID, invalidKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithInvalidToken() throws Exception {
        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(TOKEN);
    }

    @Test(expected = ProfileException.class)
    public void getActivityDetails_shouldFailWithNullToken() throws Exception {
        SecureYotiClient testObj = new SecureYotiClient(APP_ID, validKeyPairSource, profileServiceMock, remoteAmlServiceMock, attributeConverterMock);
        testObj.getActivityDetails(null);
    }

    private byte[] createProfileData() {
        return AttributeList.newBuilder()
                .addAttributes(STRING_ATTRIBUTE_PROTO)
                .addAttributes(DATE_ATTRIBUTE_PROTO)
                .addAttributes(JSON_ATTRIBUTE_PROTO)
                .build().toByteArray();
    }

    private static AttrProto.Attribute createAttribute(String name) {
        return AttrProto.Attribute.newBuilder()
                .setName(name)
                .build();
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
