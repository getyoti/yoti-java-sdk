package com.yoti.api.client;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AttributeTest {
    private static final String NAME = "test";
    private static final String TEST_VALUE = "test-value";

    @Test
    public void shouldReturnValue() {
        Attribute a = new Attribute(NAME, TEST_VALUE);
        assertEquals(TEST_VALUE, a.getValue(String.class));
    }

    @Test
    public void shouldReturnNullIfValueIsNull() {
        Attribute a = new Attribute(NAME, null);
        assertNull(a.getValue(String.class));
    }

    @Test
    public void shouldReturnNullIfValueTypeIsDifferent() {
        Attribute a = new Attribute(NAME, "testString");
        assertNull(a.getValue(Integer.class));
    }

    @Test
    public void shouldReturnValueNotDefault() {
        Attribute a = new Attribute(NAME, TEST_VALUE);
        assertEquals(TEST_VALUE, a.getValueOrDefault(String.class, "ignored"));
    }

    @Test
    public void shouldReturnDefaultIfValueTypeIsDifferent() {
        Attribute a = new Attribute(NAME, "testString");
        assertThat(1, is(a.getValueOrDefault(Integer.class, 1)));
    }

    @Test
    public void shouldReturnDefaultIfValueIsNull() {
        Attribute a = new Attribute(NAME, null);
        assertThat(1, is(a.getValueOrDefault(Integer.class, 1)));
    }
}
