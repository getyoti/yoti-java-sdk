package com.yoti.api.client.spi.remote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumanProfileAdapterTest {

    private static final String GIVEN_NAMES = "Given Names";
    private static final String GENDER_MALE = "MALE";
    private static final String GENDER_INVALID = "X";
    private static final String VALID_DOCUMENT_DETAILS = "PASSPORT GBR 12345abc 2016-05-01";
    private static final String INVALID_DOCUMENT_DETAILS = "invalid";
    private static final String FAMILY_NAME = "Family Name";

    HumanProfileAdapter testObj;

    @Mock Profile profileMock;

    @Before
    public void setUp() throws Exception {
        testObj = (HumanProfileAdapter) HumanProfileAdapter.wrap(profileMock);
    }

    @Test
    public void getGender_shouldReturnGender() {
        when(profileMock.getAttribute("gender")).thenReturn(GENDER_MALE);

        HumanProfile.Gender result = testObj.getGender();

        assertEquals(HumanProfile.Gender.MALE, result);
    }

    @Test
    public void getGender_shouldNotReturnGenderForNullAttribute() {
        HumanProfile.Gender result = testObj.getGender();

        assertNull(result);
    }

    @Test
    public void getGender_shouldNotReturnGenderForInvalidAttribute() {
        when(profileMock.getAttribute("gender")).thenReturn(GENDER_INVALID);

        HumanProfile.Gender result = testObj.getGender();

        assertEquals(HumanProfile.Gender.OTHER, result);
    }

    @Test
    public void getDocumentDetails_shouldReturnDocumentDetails() {
        when(profileMock.getAttribute("document_details")).thenReturn(VALID_DOCUMENT_DETAILS);

        DocumentDetails result = testObj.getDocumentDetails();

        assertEquals(DocumentDetails.DocumentType.PASSPORT, result.getType());
    }

    @Test
    public void getDocumentDetails_shouldNotReturnDocumentDetailsForNullAttribute() {
        DocumentDetails result = testObj.getDocumentDetails();

        assertNull(result);
    }

    @Test
    public void getDocumentDetails_shouldNotReturnDocumentDetailsForInvalidAttribute() {
        when(profileMock.getAttribute("document_details")).thenReturn(INVALID_DOCUMENT_DETAILS);

        DocumentDetails result = testObj.getDocumentDetails();

        assertNull(result);
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldReturnNullWhenOtherAttributesAreBothNull() {
        when(profileMock.getAttribute("given_names")).thenReturn(null);
        when(profileMock.getAttribute("family_name")).thenReturn(null);

        String result = testObj.getGivenAndLastNames();

        assertNull(result);
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldWorkWithNullFamilyName() {
        when(profileMock.getAttribute("given_names")).thenReturn(GIVEN_NAMES);
        when(profileMock.getAttribute("family_name")).thenReturn(null);

        String result = testObj.getGivenAndLastNames();

        assertThat(result, is(GIVEN_NAMES));
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldWorkWithNullGivenNames() {
        when(profileMock.getAttribute("given_names")).thenReturn(null);
        when(profileMock.getAttribute("family_name")).thenReturn(FAMILY_NAME);

        String result = testObj.getGivenAndLastNames();

        assertThat(result, is(FAMILY_NAME));
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldWorkWithValidGivenAndFamilyNames() {
        when(profileMock.getAttribute("given_names")).thenReturn(GIVEN_NAMES);
        when(profileMock.getAttribute("family_name")).thenReturn(FAMILY_NAME);

        String result = testObj.getGivenAndLastNames();

        assertThat(result, is(GIVEN_NAMES + " " + FAMILY_NAME));
    }

}
