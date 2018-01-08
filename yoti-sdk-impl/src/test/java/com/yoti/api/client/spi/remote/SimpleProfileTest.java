package com.yoti.api.client.spi.remote;

import com.yoti.api.client.Profile;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SimpleProfileTest {

    private static final String KEY = "testKey";
    private static final String STRING_VALUE = "test value";
    private static final Integer INTEGER_VALUE = 1;

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailConstructionForNullAttributes() {
        new SimpleProfile(null);
    }

    @Test
    public void is_shouldReturnBooleanValueForExistingKey() {
        Profile profile = new SimpleProfile(map(KEY, TRUE));

        boolean result = profile.is(KEY, false);

        assertTrue(result);
    }

    @Test
    public void is_shouldReturnDefaultBooleanForNonExistingKey() {
        Map<String, Object> m = Collections.emptyMap();
        Profile profile = new SimpleProfile(m);

        boolean result = profile.is(KEY, false);

        assertFalse(result);
    }

    @Test
    public void is_shouldReturnDefaultBooleanForMismatchingType() {
        Profile p = new SimpleProfile(map(KEY, "String"));

        boolean result = p.is(KEY, false);

        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void is_shouldThrowExceptionForNullAttributeName() {
        Map<String, Object> m = Collections.emptyMap();
        Profile profile = new SimpleProfile(m);

        profile.is(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttribute_shouldThrowExceptionForNullAttributeName() {
        Map<String, Object> m = Collections.emptyMap();
        Profile profile = new SimpleProfile(m);

        profile.getAttribute(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeTyped_shouldThrowExceptionForNullAttributeName() {
        Map<String, Object> m = Collections.emptyMap();
        Profile profile = new SimpleProfile(m);

        profile.getAttribute(null, String.class);
    }

    @Test
    public void shouldReturnStringValueForExistingKey() {
        Profile profile = new SimpleProfile(map(KEY, STRING_VALUE));

        String result = profile.getAttribute(KEY);

        assertEquals(STRING_VALUE, result);
    }

    @Test
    public void shouldReturnNullValueForNonExistingKey() {
        Map<String, Object> map = Collections.emptyMap();
        Profile profile = new SimpleProfile(map);

        assertNull(profile.getAttribute(KEY));
        assertNull(profile.getAttribute(KEY, Integer.class));
    }

    @Test
    public void shouldReturnNullValueForMismatchingType() {
        Profile profile = new SimpleProfile(map(KEY, INTEGER_VALUE));

        assertNull(profile.getAttribute(KEY));
        assertNull(profile.getAttribute(KEY, String.class));
    }

    @Test
    public void shouldReturnIntegerValueForExistingKey() {
        Profile profile = new SimpleProfile(map(KEY, INTEGER_VALUE));

        Integer result = profile.getAttribute(KEY, Integer.class);

        assertEquals(INTEGER_VALUE, result);
    }

    private Map<String, Object> map(String key, Object o) {
        return singletonMap(key, o);
    }
}
