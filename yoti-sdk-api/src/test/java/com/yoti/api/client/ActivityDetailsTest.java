package com.yoti.api.client;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.bouncycastle.util.encoders.Base64;
import org.junit.*;
import org.mockito.*;

public class ActivityDetailsTest {

    private static final String REMEMBER_ME = "someRememberMeId";
    private static final String PARENT_REMEMBER_ME = "someParentRememberMeId";
    private static final HumanProfile USER_PROFILE = Mockito.mock(HumanProfile.class);
    private static final ApplicationProfile APP_PROFILE = Mockito.mock(ApplicationProfile.class);
    private static final ExtraData EXTRA_DATA = Mockito.mock(ExtraData.class);
    private static final byte[] RECEIPT_ID = { 1, 2, 3, 4, 5, 6, 7, 8 };
    private static final String RECEIPT_ID_STRING = Base64.toBase64String(RECEIPT_ID);
    private static final Date TIMESTAMP = new Date();

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullTimestamp() {
        new ActivityDetails(REMEMBER_ME, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, null, RECEIPT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullReceiptId() {
        new ActivityDetails(REMEMBER_ME, null, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, null);
    }

    @Test
    public void shouldReturnUserId() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(REMEMBER_ME, s.getRememberMeId());
    }

    @Test
    public void shouldReturnUserProfile() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(USER_PROFILE, s.getUserProfile());
    }

    @Test
    public void shouldReturnAppProfile() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(APP_PROFILE, s.getApplicationProfile());
    }

    @Test
    public void shouldReturnReceiptId() {
        ActivityDetails s = new ActivityDetails(REMEMBER_ME, PARENT_REMEMBER_ME, USER_PROFILE, APP_PROFILE, EXTRA_DATA, TIMESTAMP, RECEIPT_ID);
        assertEquals(RECEIPT_ID_STRING, s.getReceiptId());
    }

}
