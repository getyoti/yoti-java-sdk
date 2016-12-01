package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import com.yoti.api.client.Profile;

public class SimpleProfileTest {
    private static final String KEY = "testKey";
    private static final String STRING_VALUE = "test value";
    private static final Integer INTEGER_VALUE = 1;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailConstructionForNullAttributes() {
        new SimpleProfile(null);
    }

    @Test
    public void shouldReturnBooleanValueForExistingKey() {
        Profile p = new SimpleProfile(map(KEY, TRUE));
        assertEquals(TRUE, p.is(KEY, false));
    }

    @Test
    public void shouldReturnDefaultBooleanForNonExistingKey() {
        Map<String, Object> m = Collections.emptyMap();
        Profile p = new SimpleProfile(m);
        assertEquals(FALSE, p.is(KEY, false));
    }

    @Test
    public void shouldReturnDefaultBooleanForMismatchingType() {
        Profile p = new SimpleProfile(map(KEY, "String"));
        assertEquals(FALSE, p.is(KEY, false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullAttributeName() {
        Map<String, Object> m = Collections.emptyMap();
        Profile p = new SimpleProfile(m);
        p.is(null, false);
        p.getAttribute(null);
        p.getAttribute(null, String.class);
    }

    @Test
    public void shouldReturnStringValueForExistingKey() {
        Profile p = new SimpleProfile(map(KEY, STRING_VALUE));
        assertEquals(STRING_VALUE, p.getAttribute(KEY));
    }

    @Test
    public void shouldReturnNullValueForNonExistingKey() {
        Map<String, Object> m = Collections.emptyMap();
        Profile p = new SimpleProfile(m);
        assertNull(p.getAttribute(KEY));
        assertNull(p.getAttribute(KEY, Integer.class));
    }

    @Test
    public void shouldReturnNullValueForMismatchingType() {
        Profile p = new SimpleProfile(map(KEY, INTEGER_VALUE));
        assertNull(p.getAttribute(KEY));
        assertNull(p.getAttribute(KEY, String.class));
    }

    @Test
    public void shouldReturnIntegerValueForExistingKey() {
        Profile p = new SimpleProfile(map(KEY, INTEGER_VALUE));
        assertEquals(INTEGER_VALUE, p.getAttribute(KEY, Integer.class));
    }

    private Map<String, Object> map(String key, Object o) {
        return singletonMap(key, o);
    }
}
