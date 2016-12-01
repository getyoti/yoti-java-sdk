package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;

public class HumanProfileAdapterTest {
    private static String GENDER_MALE = "MALE";
    private static String GENDER_INVALID = "X";
    private static String VALID_DOCUMENT_DETAILS = "PASSPORT GBR 12345abc 2016-05-01";
    private static String INVALID_DOCUMENT_DETAILS = "invalid";

    @Test
    public void shouldReturnGender() {
        Profile profile = mock(Profile.class);
        Mockito.when(profile.getAttribute("gender")).thenReturn(GENDER_MALE);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertEquals(HumanProfile.Gender.MALE, p.getGender());
    }

    @Test
    public void shouldNotReturnGenderForNullAttribute() {
        Profile profile = mock(Profile.class);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertNull(p.getGender());
    }

    @Test
    public void shouldNotReturnGenderForInvalidAttribute() {
        Profile profile = mock(Profile.class);
        Mockito.when(profile.getAttribute("gender")).thenReturn(GENDER_INVALID);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertEquals(HumanProfile.Gender.OTHER, p.getGender());
    }

    @Test
    public void shouldReturnDocumentDetails() {
        Profile profile = mock(Profile.class);
        Mockito.when(profile.getAttribute("document_details")).thenReturn(VALID_DOCUMENT_DETAILS);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertEquals(DocumentDetails.DocumentType.PASSPORT, p.getDocumentDetails().getType());
    }

    @Test
    public void shouldNotReturnDocumentDetailsForNullAttribute() {
        Profile profile = mock(Profile.class);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        Assert.assertNull(p.getDocumentDetails());
    }

    @Test
    public void shouldNotReturnDocumentDetailsForInvalidAttribute() {
        Profile profile = mock(Profile.class);
        Mockito.when(profile.getAttribute("document_details")).thenReturn(INVALID_DOCUMENT_DETAILS);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        Assert.assertNull(p.getDocumentDetails());
    }
}
