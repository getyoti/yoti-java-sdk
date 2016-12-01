package com.yoti.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.yoti.api.client.Attribute;

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
    public void shouldReturnNullIfValueTypeIsDiffernet() {
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
        assertEquals(new Integer(1), a.getValueOrDefault(Integer.class, new Integer(1)));
    }

    @Test
    public void shouldReturnDefaultIfValueIsNull() {
        Attribute a = new Attribute(NAME, null);
        assertEquals(new Integer(1), a.getValueOrDefault(Integer.class, new Integer(1)));
    }
}
