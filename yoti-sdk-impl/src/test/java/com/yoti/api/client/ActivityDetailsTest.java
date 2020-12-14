package com.yoti.api.client;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.yoti.api.client.spi.remote.ApplicationProfileAdapter;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityDetailsTest {

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
        new ActivityDetails(null, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullUserProfile() {
        new ActivityDetails(REMEMBER_ME, null, null, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullAppProfile() {
        new ActivityDetails(REMEMBER_ME, null, USER_PROFILE, null, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullTimestamp() {
        new ActivityDetails(REMEMBER_ME, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, null, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullReceiptId() {
        new ActivityDetails(REMEMBER_ME, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullProfile() {
        new ActivityDetails(REMEMBER_ME, null, null, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
    }

    @Test
    public void shouldReturnUserId() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(REMEMBER_ME, s.getRememberMeId());
    }

    @Test
    public void shouldReturnUserProfile() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(USER_PROFILE_WRAPPER, s.getUserProfile());
    }

    @Test
    public void shouldReturnAppProfile() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, APP_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(APP_PROFILE_WRAPPER, s.getApplicationProfile());
    }

    @Test
    public void shouldReturnReceiptId() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(RECEIPT_ID_STRING, s.getReceiptId());
    }

}
