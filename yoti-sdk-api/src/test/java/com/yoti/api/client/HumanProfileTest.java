package com.yoti.api.client;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumanProfileTest {

    private static final String SOME_KEY = "someKey";
    private static final String STARTS_WITH = "startsWith";
    private static final String STRING_VALUE = "test value";
    private static final Integer INTEGER_VALUE = 1;

    private static final String ATTR_AGE_OVER = "age_over:";
    private static final String ATTR_AGE_UNDER = "age_under:";

    @InjectMocks HumanProfile testObj;

    @Mock Profile profileMock;

    Attribute<String> ageUnder18Attribute = new Attribute<>("age_under:18", "false");
    Attribute<String> ageUnder21Attribute = new Attribute<>("age_under:21", "true");
    Attribute<String> ageOver18Attribute = new Attribute<>("age_over:18", "true");
    Attribute<String> ageOver21Attribute = new Attribute<>("age_over:21", "false");
    List<Attribute<String>> ageUnderAttributes = asList(ageUnder18Attribute, ageUnder21Attribute);
    List<Attribute<String>> ageOverAttributes = asList(ageOver18Attribute, ageOver21Attribute);

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailConstructionForNullAttributes() {
        new HumanProfile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttribute_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(Collections.emptyList());

        profile.getAttribute(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByName_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(Collections.emptyList());

        profile.getAttribute(null);
    }

    @Test
    public void getAttribute_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttributeByName_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttribute_shouldReturnValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<?> result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<?> result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeTyped_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(Collections.emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByNameTyped_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(Collections.emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test
    public void getAttributeTyped_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeTyped_shouldReturnTypedValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnTypedValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeTyped_shouldThrowExceptionForForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        try {
            profile.getAttribute(SOME_KEY, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getAttributeByNameTyped_shouldThrowExceptionForForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        try {
            profile.getAttribute(SOME_KEY, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }
        fail("Expected an exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributesStartingWith_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributesStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributesStartingWith_shouldReturnEmptyListWhenNoMatches() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAttributesStartingWith_shouldThrowExceptionForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH, INTEGER_VALUE));

        try {
            profile.findAttributesStartingWith(STARTS_WITH, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }

        fail("Expected an exception");
    }

    @Test
    public void findAttributesStartingWith_shouldReturnTypedValuesForMatchingKey() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertEquals(1, result.size());
        assertEquals(INTEGER_VALUE, result.get(0).getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributeStartingWith_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributeStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributeStartingWith_shouldReturnNullWhenNoMatchingName() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertNull(result);
    }

    @Test
    public void findAttributeStartingWith_shouldThrowExceptionForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH, INTEGER_VALUE));

        try {
            profile.findAttributeStartingWith(STARTS_WITH, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }

        fail("Expected an exception");
    }

    @Test
    public void findAttributeStartingWith_shouldReturnTypedValueForMatchingKey() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertEquals(INTEGER_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnFirstMatchingAttributeWhenMultipleWithSameName() {
        List<Attribute<?>> attributeList = new ArrayList<>();
        attributeList.add(createAttribute("some_attribute", "firstValue"));
        attributeList.add(createAttribute("some_attribute", "secondValue"));

        Profile profile = new HumanProfile(attributeList);

        Attribute<String> result = profile.getAttribute("some_attribute", String.class);

        assertEquals("firstValue", result.getValue());
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
    
    private static <T> List<Attribute<?>> asAttributeList(String key, T o) {
        Attribute<?> a = createAttribute(key, o);
        return Collections.singletonList(a);
    }

    private static <T> Attribute<?> createAttribute(String key, T o) {
        return new Attribute<>(key, o);
    }

}
