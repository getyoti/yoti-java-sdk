package com.yoti.api.client.spi.remote;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.Attribute;

import org.junit.Test;

public class SimpleProfileTest {

    private static final String SOME_KEY = "someKey";
    private static final String STARTS_WITH = "startsWith";
    private static final String STRING_VALUE = "test value";
    private static final Integer INTEGER_VALUE = 1;

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailConstructionForNullAttributes() {
        new SimpleProfile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttribute_shouldThrowExceptionForNullAttributeName() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        profile.getAttribute(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByName_shouldThrowExceptionForNullAttributeName() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        profile.getAttributeByName(null);
    }

    @Test
    public void getAttribute_shouldReturnNullValueForNonExistingKey() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttributeByName_shouldReturnNullValueForNonExistingKey() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        assertNull(profile.getAttributeByName(SOME_KEY));
    }

    @Test
    public void getAttribute_shouldReturnValueForExistingKey() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnValueForExistingKey() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute result = profile.getAttributeByName(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeTyped_shouldThrowExceptionForNullAttributeName() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByNameTyped_shouldThrowExceptionForNullAttributeName() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        profile.getAttributeByName(null, Object.class);
    }

    @Test
    public void getAttributeTyped_shouldReturnNullValueForNonExistingKey() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnNullValueForNonExistingKey() {
        SimpleProfile profile = new SimpleProfile(Collections.<Attribute<?>>emptyList());

        assertNull(profile.getAttributeByName(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeTyped_shouldReturnTypedValueForExistingKey() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnTypedValueForExistingKey() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttributeByName(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeTyped_shouldThrowExceptionForForMismatchingType() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

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
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        try {
            profile.getAttributeByName(SOME_KEY, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }
        fail("Expected an exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributesStartingWith_shouldThrowExceptionForNullAttributeName() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributesStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributesStartingWith_shouldReturnEmptyListWhenNoMatches() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAttributesStartingWith_shouldThrowExceptionForMismatchingType() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(STARTS_WITH, INTEGER_VALUE));

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
        SimpleProfile profile = new SimpleProfile(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertEquals(result.size(), 1);
        assertEquals(INTEGER_VALUE, result.get(0).getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributeStartingWith_shouldThrowExceptionForNullAttributeName() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributeStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributeStartingWith_shouldReturnNullWhenNoMatchingName() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertNull(result);
    }

    @Test
    public void findAttributeStartingWith_shouldThrowExceptionForMismatchingType() {
        SimpleProfile profile = new SimpleProfile(asAttributeList(STARTS_WITH, INTEGER_VALUE));

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
        SimpleProfile profile = new SimpleProfile(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertEquals(INTEGER_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnFirstMatchingAttributeWhenMultipleWithSameName() {
        List<Attribute<?>> attributeList = new ArrayList<>();
        attributeList.add(createAttribute("some_attribute", "firstValue"));
        attributeList.add(createAttribute("some_attribute", "secondValue"));

        SimpleProfile profile = new SimpleProfile(attributeList);

        Attribute<String> result = profile.getAttributeByName("some_attribute", String.class);

        assertEquals(result.getValue(), "firstValue");
    }

    @Test
    public void getAttributesByName_shouldReturnAllMatchingAttributesWhenMultipleWithSameName() {
        List<Attribute<?>> attributeList = new ArrayList<>();
        attributeList.add(createAttribute("some_attribute", "firstValue"));
        attributeList.add(createAttribute("some_attribute", "secondValue"));

        SimpleProfile profile = new SimpleProfile(attributeList);

        List<Attribute<String>> result = profile.getAttributesByName("some_attribute", String.class);

        assertThat(result, hasSize(2));
        assertEquals(result.get(0).getValue(), "firstValue");
        assertEquals(result.get(1).getValue(), "secondValue");
    }

    private static <T> List<Attribute<?>> asAttributeList(String key, T o) {
        Attribute<?> a = createAttribute(key, o);
        return Collections.<Attribute<?>>singletonList(a);
    }

    private static <T> Attribute<?> createAttribute(String key, T o) {
        return new SimpleAttribute(key, o);
    }

}
