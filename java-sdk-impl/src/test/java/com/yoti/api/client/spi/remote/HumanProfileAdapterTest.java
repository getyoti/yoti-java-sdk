package com.yoti.api.client.spi.remote;

import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HumanProfileAdapterTest {
    private static final String GIVEN_NAMES = "Given Names";
    private static final String GENDER_MALE = "MALE";
    private static final String GENDER_INVALID = "X";
    private static final String VALID_DOCUMENT_DETAILS = "PASSPORT GBR 12345abc 2016-05-01";
    private static final String INVALID_DOCUMENT_DETAILS = "invalid";
    private static final String FAMILY_NAME = "Family Name";

    @Test
    public void shouldReturnGender() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("gender")).thenReturn(GENDER_MALE);
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
        when(profile.getAttribute("gender")).thenReturn(GENDER_INVALID);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertEquals(HumanProfile.Gender.OTHER, p.getGender());
    }

    @Test
    public void shouldReturnDocumentDetails() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("document_details")).thenReturn(VALID_DOCUMENT_DETAILS);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertEquals(DocumentDetails.DocumentType.PASSPORT, p.getDocumentDetails().getType());
    }

    @Test
    public void shouldNotReturnDocumentDetailsForNullAttribute() {
        Profile profile = mock(Profile.class);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertNull(p.getDocumentDetails());
    }

    @Test
    public void shouldNotReturnDocumentDetailsForInvalidAttribute() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("document_details")).thenReturn(INVALID_DOCUMENT_DETAILS);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertNull(p.getDocumentDetails());
    }

    @Test
    public void getGivenAndLastNamesShouldReturnNullWhenOtherAttributesAreBothNull() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("given_names")).thenReturn(null);
        when(profile.getAttribute("family_name")).thenReturn(null);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertNull(p.getGivenAndLastNames());
    }

    @Test
    public void getGivenAndLastNamesShouldWorkWithNullFamilyName() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("given_names")).thenReturn(GIVEN_NAMES);
        when(profile.getAttribute("family_name")).thenReturn(null);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertThat(p.getGivenAndLastNames(), is(GIVEN_NAMES));
    }

    @Test
    public void getGivenAndLastNamesShouldWorkWithNullGivenNames() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("given_names")).thenReturn(null);
        when(profile.getAttribute("family_name")).thenReturn(FAMILY_NAME);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertThat(p.getGivenAndLastNames(), is(FAMILY_NAME));
    }

    @Test
    public void getGivenAndLastNamesShouldWorkWithValidGivenAndFamilyNames() {
        Profile profile = mock(Profile.class);
        when(profile.getAttribute("given_names")).thenReturn(GIVEN_NAMES);
        when(profile.getAttribute("family_name")).thenReturn(FAMILY_NAME);
        HumanProfile p = HumanProfileAdapter.wrap(profile);
        assertThat(p.getGivenAndLastNames(), is(GIVEN_NAMES + " " + FAMILY_NAME));
    }
}
