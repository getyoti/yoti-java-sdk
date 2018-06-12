package com.yoti.api.client.spi.remote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
    private static final String SOME_GENDER = "someGender";
    private static final String VALID_DOCUMENT_DETAILS = "PASSPORT GBR 12345abc 2016-05-01";
    private static final String INVALID_DOCUMENT_DETAILS = "invalid";

    @InjectMocks HumanProfileAdapter testObj;

    @Mock Profile profileMock;
    @Mock DocumentDetailsAttributeParser documentDetailsAttributeParserMock;
    @Mock GenderParser genderParserMock;

    @Mock DocumentDetails documentDetailsMock;

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
    public void getGender_shouldReturnGenderFromParser() {
        when(profileMock.getAttribute(ATTR_GENDER)).thenReturn(SOME_GENDER);
        when(genderParserMock.parseFrom(SOME_GENDER)).thenReturn(HumanProfile.Gender.OTHER);

        HumanProfile.Gender result = testObj.getGender();

        Assert.assertEquals(HumanProfile.Gender.OTHER, result);
    }

    @Test
    public void getDocumentDetails_shouldReturnDocumentDetails() throws Exception {
        when(profileMock.getAttribute(ATTR_DOCUMENT_DETAILS)).thenReturn(VALID_DOCUMENT_DETAILS);
        when(documentDetailsAttributeParserMock.parseFrom(VALID_DOCUMENT_DETAILS)).thenReturn(documentDetailsMock);

        DocumentDetails result = testObj.getDocumentDetails();

        assertSame(documentDetailsMock, result);
    }

    @Test
    public void getDocumentDetails_shouldNotReturnDocumentDetailsForNullAttribute() throws Exception {
        when(profileMock.getAttribute(ATTR_DOCUMENT_DETAILS)).thenReturn(null);
        when(documentDetailsAttributeParserMock.parseFrom(null)).thenReturn(null);

        DocumentDetails result = testObj.getDocumentDetails();

        assertNull(result);
    }

    @Test
    public void getDocumentDetails_shouldSwallowExceptionCausedByInvalidAttribute() throws Exception {
        when(profileMock.getAttribute(ATTR_DOCUMENT_DETAILS)).thenReturn(INVALID_DOCUMENT_DETAILS);
        when(documentDetailsAttributeParserMock.parseFrom(INVALID_DOCUMENT_DETAILS)).thenThrow(new ParseException("someMessage", 1));

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
