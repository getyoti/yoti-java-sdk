package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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

    private static final String ATTR_AGE_OVER = "age_over:";
    private static final String ATTR_AGE_UNDER = "age_under:";
    private static final String ATTR_GENDER = "gender";
    private static final String ATTR_DOCUMENT_DETAILS = "document_details";
    private static final String ATTR_GIVEN_NAMES = "given_names";
    private static final String ATTR_FAMILY_NAME = "family_name";

    private static final String SOME_GIVEN_NAMES = "Some Given Names";
    private static final String SOME_FAMILY_NAME = "Some Family Name";
    private static final String GENDER_MALE = "MALE";
    private static final String GENDER_INVALID = "X";
    private static final String VALID_DOCUMENT_DETAILS = "PASSPORT GBR 12345abc 2016-05-01";
    private static final String INVALID_DOCUMENT_DETAILS = "invalid";

    HumanProfileAdapter testObj;

    @Mock Profile profileMock;

    @Before
    public void setUp() throws Exception {
        testObj = (HumanProfileAdapter) HumanProfileAdapter.wrap(profileMock);
    }

    @Test
    public void isAgeVerified_shouldReturnNullWhenBothAttributesAreMissing() {
        Boolean result = testObj.isAgeVerified();

        assertNull(result);
    }

    @Test
    public void isAgeVerified_shouldReturnValueOfAgeOver() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_OVER, String.class)).thenReturn("true");

        Boolean result = testObj.isAgeVerified();

        assertTrue(result);
    }

    @Test
    public void isAgeVerified_shouldReturnValueOfAgeUnder() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_UNDER, String.class)).thenReturn("false");

        Boolean result = testObj.isAgeVerified();

        assertFalse(result);
    }

    // This scenario should never happen, but to be pragmatic...
    @Test
    public void isAgeVerified_shouldPreferAgeOverWhenBothAttributesArePresent() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_OVER, String.class)).thenReturn("true");
        when(profileMock.findAttributeStartingWith(ATTR_AGE_UNDER, String.class)).thenReturn("false");

        Boolean result = testObj.isAgeVerified();

        assertTrue(result);
    }

    @Test
    public void getGender_shouldReturnGender() {
        when(profileMock.getAttribute(ATTR_GENDER)).thenReturn(GENDER_MALE);

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
        when(profileMock.getAttribute(ATTR_GENDER)).thenReturn(GENDER_INVALID);

        HumanProfile.Gender result = testObj.getGender();

        assertEquals(HumanProfile.Gender.OTHER, result);
    }

    @Test
    public void getDocumentDetails_shouldReturnDocumentDetails() {
        when(profileMock.getAttribute(ATTR_DOCUMENT_DETAILS)).thenReturn(VALID_DOCUMENT_DETAILS);

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
        when(profileMock.getAttribute(ATTR_DOCUMENT_DETAILS)).thenReturn(INVALID_DOCUMENT_DETAILS);

        DocumentDetails result = testObj.getDocumentDetails();

        assertNull(result);
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldReturnNullWhenOtherAttributesAreBothNull() {
        when(profileMock.getAttribute(ATTR_GIVEN_NAMES)).thenReturn(null);
        when(profileMock.getAttribute(ATTR_FAMILY_NAME)).thenReturn(null);

        String result = testObj.getGivenAndLastNames();

        assertNull(result);
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldWorkWithNullFamilyName() {
        when(profileMock.getAttribute(ATTR_GIVEN_NAMES)).thenReturn(SOME_GIVEN_NAMES);
        when(profileMock.getAttribute(ATTR_FAMILY_NAME)).thenReturn(null);

        String result = testObj.getGivenAndLastNames();

        assertThat(result, is(SOME_GIVEN_NAMES));
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldWorkWithNullGivenNames() {
        when(profileMock.getAttribute(ATTR_GIVEN_NAMES)).thenReturn(null);
        when(profileMock.getAttribute(ATTR_FAMILY_NAME)).thenReturn(SOME_FAMILY_NAME);

        String result = testObj.getGivenAndLastNames();

        assertThat(result, is(SOME_FAMILY_NAME));
    }

    @Test
    public void getGivenAndLastNames_getGivenAndLastNamesShouldWorkWithValidGivenAndFamilyNames() {
        when(profileMock.getAttribute(ATTR_GIVEN_NAMES)).thenReturn(SOME_GIVEN_NAMES);
        when(profileMock.getAttribute(ATTR_FAMILY_NAME)).thenReturn(SOME_FAMILY_NAME);

        String result = testObj.getGivenAndLastNames();

        assertThat(result, is(SOME_GIVEN_NAMES + " " + SOME_FAMILY_NAME));
    }

}
