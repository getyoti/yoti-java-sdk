package com.yoti.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ProfileTest {

    private static final String SOME_KEY = "someKey";
    private static final String STARTS_WITH = "startsWith";
    private static final String STRING_VALUE = "test value";
    private static final Integer INTEGER_VALUE = 1;

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailConstructionForNullAttributes() {
        new ProfileImpl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttribute_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        profile.getAttribute(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByName_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        profile.getAttribute(null);
    }

    @Test
    public void getAttribute_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttributeByName_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttribute_shouldReturnValueForExistingKey() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<?> result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnValueForExistingKey() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<?> result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeTyped_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByNameTyped_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test
    public void getAttributeTyped_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new ProfileImpl(Collections.emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeTyped_shouldReturnTypedValueForExistingKey() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnTypedValueForExistingKey() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeTyped_shouldThrowExceptionForForMismatchingType() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, INTEGER_VALUE));

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
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, INTEGER_VALUE));

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
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributesStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributesStartingWith_shouldReturnEmptyListWhenNoMatches() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAttributesStartingWith_shouldThrowExceptionForMismatchingType() {
        Profile profile = new ProfileImpl(asAttributeList(STARTS_WITH, INTEGER_VALUE));

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
        Profile profile = new ProfileImpl(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertEquals(1, result.size());
        assertEquals(INTEGER_VALUE, result.get(0).getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributeStartingWith_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributeStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributeStartingWith_shouldReturnNullWhenNoMatchingName() {
        Profile profile = new ProfileImpl(asAttributeList(SOME_KEY, INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertNull(result);
    }

    @Test
    public void findAttributeStartingWith_shouldThrowExceptionForMismatchingType() {
        Profile profile = new ProfileImpl(asAttributeList(STARTS_WITH, INTEGER_VALUE));

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
        Profile profile = new ProfileImpl(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertEquals(INTEGER_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnFirstMatchingAttributeWhenMultipleWithSameName() {
        List<Attribute<?>> attributeList = new ArrayList<>();
        attributeList.add(createAttribute("some_attribute", "firstValue"));
        attributeList.add(createAttribute("some_attribute", "secondValue"));

        Profile profile = new ProfileImpl(attributeList);

        Attribute<String> result = profile.getAttribute("some_attribute", String.class);

        assertEquals("firstValue", result.getValue());
    }

    private static <T> List<Attribute<?>> asAttributeList(String key, T o) {
        Attribute<?> a = createAttribute(key, o);
        return Collections.singletonList(a);
    }

    private static <T> Attribute<?> createAttribute(String key, T o) {
        return new Attribute<>(key, o);
    }

}
