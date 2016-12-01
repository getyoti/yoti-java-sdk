package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptSymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKey;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

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

public class SecureYotiClientTest {
    private static final Map<String, String> PROFILE_ATTRIBUTES;

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

    @Test
    public void shouldGetCorrectProfile() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(er.data, er.iv);
        byte[] wrappedReceiptKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(wrappedReceiptKey).withOtherPartyProfile(profileContent)
                .withProfile(profileContent).withTimestamp(TIMESTAMP).withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS).build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        ActivityDetails activityDetails = client.getActivityDetails(encryptedToken);
        assertNotNull(activityDetails);
        Profile profile = activityDetails.getUserProfile();

        assertNotNull(profile);
        assertProfileAttributes(profile, PROFILE_ATTRIBUTES);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithInvalidTimestamp() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(er.data, er.iv);
        byte[] wrappedReceiptKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID)
                .withWrappedReceiptKey(wrappedReceiptKey).withOtherPartyProfile(profileContent)
                .withProfile(profileContent).withTimestamp(INVALID_TIMESTAMP).withReceiptId(RECEIPT_ID)
                .withOutcome(Receipt.Outcome.SUCCESS).build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithNoIV() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(er.data, null);
        byte[] wrappedKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID).withWrappedReceiptKey(wrappedKey)
                .withOtherPartyProfile(profileContent).build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithInvalidIV() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(er.data, new byte[] { 1, 2 });
        byte[] wrappedKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        Receipt receipt = new Receipt.Builder().withRememberMeId(USER_ID).withWrappedReceiptKey(wrappedKey)
                .withOtherPartyProfile(profileContent).build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithNoReceipt() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(null);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ActivityFailureException.class)
    public void shouldFailWithFailureReceipt() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        Receipt receipt = new Receipt.Builder().withOutcome(Receipt.Outcome.FAILURE).build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);

    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithInvalidProfileData() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(new byte[] { 1, 2 }, er.iv);
        byte[] wrappedKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(wrappedKey).withOtherPartyProfile(profileContent)
                .build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithInvalidReceiptKey() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(er.data, er.iv);
        byte[] wrappedKey = { 1, 2, 3 };
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(wrappedKey).withOtherPartyProfile(profileContent)
                .build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithNoReceiptKey() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(er.data, er.iv);
        byte[] wrappedKey = null;
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(wrappedKey).withOtherPartyProfile(profileContent)
                .build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithNoProfileData() throws Exception {
        Key receiptKey = generateKey();
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        EncryptionResult er = encryptSymmetric(createProfileData(PROFILE_ATTRIBUTES), receiptKey);
        byte[] profileContent = marshalProfile(null, er.iv);
        byte[] wrappedKey = encryptAsymmetric(receiptKey.getEncoded(), publicKey);
        Receipt receipt = new Receipt.Builder().withWrappedReceiptKey(wrappedKey).withOtherPartyProfile(profileContent)
                .build();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenReturn(receipt);

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithExceptionFromProfileService() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();

        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        ProfileService profileService = mock(ProfileService.class);
        when(profileService.getReceipt(Mockito.<KeyPair> any(), Mockito.<String> any(), Mockito.<String> any()))
                .thenThrow(new ProfileException("Test exception"));

        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void shouldFailWhenStreamExceptionLoadingKeys() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();
        KeyPairSource keyPairSource = new StaticKeyPairSource(true);
        ProfileService profileService = mock(ProfileService.class);
        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void shouldFailWhenKeyPairSourceExceptionLoadingKeys() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();
        KeyPairSource keyPairSource = new StaticKeyPairSource(false);
        ProfileService profileService = mock(ProfileService.class);
        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullApplicationId() throws Exception {
        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
        ProfileService profileService = mock(ProfileService.class);

        new SecureYotiClient(null, keyPairSource, profileService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPairSource() throws Exception {
        ProfileService profileService = mock(ProfileService.class);

        new SecureYotiClient(APP_ID, null, profileService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullProfileService() throws Exception {
        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);

        new SecureYotiClient(APP_ID, keyPairSource, null);
    }

    @Test(expected = InitialisationException.class)
    public void shouldFailWithNoKeyPair() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();
        KeyPairSource keyPairSource = new StaticKeyPairSource("no-key-pair-in-file");
        ProfileService profileService = mock(ProfileService.class);
        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = InitialisationException.class)
    public void shouldFailWithInvalidKeyPair() throws Exception {
        PublicKey publicKey = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM).getPublic();
        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.INVALID_KEY_PAIR_PEM);
        ProfileService profileService = mock(ProfileService.class);
        String encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithInvalidToken() throws Exception {
        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
        ProfileService profileService = mock(ProfileService.class);
        String encryptedToken = TOKEN;

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(encryptedToken);
    }

    @Test(expected = ProfileException.class)
    public void shouldFailWithNullToken() throws Exception {
        KeyPairSource keyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
        ProfileService profileService = mock(ProfileService.class);

        SecureYotiClient client = new SecureYotiClient(APP_ID, keyPairSource, profileService);

        client.getActivityDetails(null);
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
