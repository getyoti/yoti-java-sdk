package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Date;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Image;
import com.yoti.api.client.Profile;

import com.yoti.api.client.ExtraData;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleActivityDetailsTest {

    private static final String REMEMBER_ME = "someRememberMeId";
    private static final String PARENT_REMEMBER_ME = "someParentRememberMeId";
    private static final Profile USER_PROFILE = Mockito.mock(Profile.class);
    private static final Profile APP_PROFILE = Mockito.mock(Profile.class);
    private static final ExtraData EXTRA_DATA = Mockito.mock(ExtraData.class);
    private static final Profile USER_PROFILE_WRAPPER = HumanProfileAdapter.wrap(USER_PROFILE);
    private static final Profile APP_PROFILE_WRAPPER = ApplicationProfileAdapter.wrap(APP_PROFILE);
    private static final byte[] RECEIPT_ID = { 1, 2, 3, 4, 5, 6, 7, 8 };
    private static final String RECEIPT_ID_STRING = Base64.toBase64String(RECEIPT_ID);
    private static final Date TIMESTAMP = new Date();
    private static final byte[] SOME_SELFIE_BYTES = "selfieTestVal".getBytes();

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullRememberMeId() {
        new SimpleActivityDetails(null, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullUserProfile() {
        new SimpleActivityDetails(REMEMBER_ME, null, null, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullAppProfile() {
        new SimpleActivityDetails(REMEMBER_ME, null, USER_PROFILE, null, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullTimestamp() {
        new SimpleActivityDetails(REMEMBER_ME, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, null, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullReceiptId() {
        new SimpleActivityDetails(REMEMBER_ME, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullProfile() {
        new SimpleActivityDetails(REMEMBER_ME, null, null, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test
    public void shouldReturnUserId() {
        SimpleActivityDetails s = new SimpleActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(REMEMBER_ME, s.getRememberMeId());
        assertEquals(REMEMBER_ME, s.getUserId());
    }

    @Test
    public void shouldReturnUserProfile() {
        SimpleActivityDetails s = new SimpleActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(USER_PROFILE_WRAPPER, s.getUserProfile());
    }

    @Test
    public void shouldReturnAppProfile() {
        SimpleActivityDetails s = new SimpleActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, APP_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(APP_PROFILE_WRAPPER, s.getApplicationProfile());
    }

    @Test
    public void shouldReturnReceiptId() {
        SimpleActivityDetails s = new SimpleActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(RECEIPT_ID_STRING, s.getReceiptId());
    }

    @Test
    public void shouldReturnBase64SelfieIfSelfieSet() {
        Attribute<Image> selfie = new SimpleAttribute("selfie", new JpegAttributeValue(SOME_SELFIE_BYTES));
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>singletonList(selfie));

        SimpleActivityDetails result = new SimpleActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, profile, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);

        String expected = "data:image/jpeg;base64," + Base64.toBase64String(SOME_SELFIE_BYTES);
        assertEquals(expected, result.getBase64Selfie());
        assertEquals(selfie.getValue().getBase64Content(), result.getBase64Selfie());
    }

    @Test
    public void shouldReturnBlankBase64SelfieIfSelfieNotSet() {
        Attribute familyName = new SimpleAttribute("family_name", "Smith");
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>singletonList(familyName));

        SimpleActivityDetails result = new SimpleActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, profile, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);

        assertEquals("", result.getBase64Selfie());
    }

}
