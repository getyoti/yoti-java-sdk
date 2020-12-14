package com.yoti.api.client;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumanProfileAdapterTest {

    private static final String ATTR_AGE_OVER = "age_over:";
    private static final String ATTR_AGE_UNDER = "age_under:";

    @InjectMocks HumanProfileAdapter testObj;

    @Mock Profile profileMock;

    Attribute<String> ageUnder18Attribute = new Attribute<>("age_under:18", "false");
    Attribute<String> ageUnder21Attribute = new Attribute<>("age_under:21", "true");
    Attribute<String> ageOver18Attribute = new Attribute<>("age_over:18", "true");
    Attribute<String> ageOver21Attribute = new Attribute<>("age_over:21", "false");
    List<Attribute<String>> ageUnderAttributes = asList(ageUnder18Attribute, ageUnder21Attribute);
    List<Attribute<String>> ageOverAttributes = asList(ageOver18Attribute, ageOver21Attribute);

    @Test
    public void isAgeVerified_shouldReturnNullWhenBothAttributesAreMissing() {
        Boolean result = testObj.isAgeVerified();

        assertNull(result);
    }

    @Test
    public void isAgeVerified_shouldReturnValueOfAgeOver() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_OVER, String.class)).thenReturn(new Attribute(ATTR_AGE_OVER, "true"));

        Boolean result = testObj.isAgeVerified();

        assertTrue(result);
    }

    @Test
    public void isAgeVerified_shouldReturnValueOfAgeUnder() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_UNDER, String.class)).thenReturn(new Attribute(ATTR_AGE_UNDER, "false"));

        Boolean result = testObj.isAgeVerified();

        assertFalse(result);
    }

    // This scenario should never happen, but to be pragmatic...
    @Test
    public void isAgeVerified_shouldPreferAgeOverWhenBothAttributesArePresent() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_OVER, String.class)).thenReturn(new Attribute<>(ATTR_AGE_OVER, "true"));
        when(profileMock.findAttributeStartingWith(ATTR_AGE_UNDER, String.class)).thenReturn(new Attribute<>(ATTR_AGE_UNDER, "false"));

        Boolean result = testObj.isAgeVerified();

        assertTrue(result);
    }

    @Test
    public void getAgeVerifications_shouldBeNullSafe() {
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)).thenReturn(EMPTY_LIST);
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)).thenReturn(EMPTY_LIST);

        List<AgeVerification> result = testObj.getAgeVerifications();

        assertThat(result, hasSize(0));
    }

    @Test
    public void getAgeVerifications_shouldOnlySearchWrappedProfileOnce() {
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)).thenReturn(ageUnderAttributes);
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)).thenReturn(ageOverAttributes);

        testObj.getAgeVerifications();
        List<AgeVerification> result = testObj.getAgeVerifications();

        assertThat(result, hasSize(4));
        assertThat(new HashSet<>(result), hasSize(4));
        assertThat(result.get(0).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        assertThat(result.get(1).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        assertThat(result.get(2).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        assertThat(result.get(3).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        verify(profileMock, times(1)).findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class);
        verify(profileMock, times(1)).findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class);
    }

    @Test
    public void findAgeOverVerification_returnsNullForNoMatch() {
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)).thenReturn(EMPTY_LIST);
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)).thenReturn(EMPTY_LIST);

        testObj.findAgeOverVerification(18);
        AgeVerification result = testObj.findAgeOverVerification(18);

        assertNull(result);
    }

    @Test
    public void findAgeOverVerification_shouldOnlySearchWrappedProfileOnce() {
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)).thenReturn(ageUnderAttributes);
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)).thenReturn(ageOverAttributes);

        testObj.findAgeOverVerification(18);
        AgeVerification result = testObj.findAgeOverVerification(18);

        assertSame(ageOver18Attribute, result.getAttribute());
        verify(profileMock, times(1)).findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class);
        verify(profileMock, times(1)).findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class);
    }

    @Test
    public void findAgeUnderVerification_returnsNullForNoMatch() {
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)).thenReturn(EMPTY_LIST);
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)).thenReturn(EMPTY_LIST);

        testObj.findAgeOverVerification(18);
        AgeVerification result = testObj.findAgeUnderVerification(18);

        assertNull(result);
    }

    @Test
    public void findAgeUnderVerification_shouldOnlySearchWrappedProfileOnce() {
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)).thenReturn(ageUnderAttributes);
        when(profileMock.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)).thenReturn(ageOverAttributes);

        testObj.findAgeUnderVerification(18);
        AgeVerification result = testObj.findAgeUnderVerification(18);

        assertSame(ageUnder18Attribute, result.getAttribute());
        verify(profileMock, times(1)).findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class);
        verify(profileMock, times(1)).findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class);
    }

}
