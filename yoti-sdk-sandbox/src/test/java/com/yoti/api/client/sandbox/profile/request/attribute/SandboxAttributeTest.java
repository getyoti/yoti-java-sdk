package com.yoti.api.client.sandbox.profile.request.attribute;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SandboxAttributeTest {

    private static final String SOME_NAME = "someName";
    private static final String SOME_DERIVATION = "someDerivation";
    private static final String SOME_VALUE = "someValue";

    @Test
    public void shouldNotBeOptionalByDefault() {
        SandboxAttribute result = SandboxAttribute.builder()
                .withName(SOME_NAME)
                .withDerivation(SOME_DERIVATION)
                .withValue(SOME_VALUE)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertEquals(SOME_DERIVATION, result.getDerivation());
        assertEquals(SOME_VALUE, result.getValue());
        assertEquals("false", result.getOptional());
    }

    @Test
    public void shouldBeOptionalWhenSpecified() {
        SandboxAttribute result = SandboxAttribute.builder()
                .withName(SOME_NAME)
                .withDerivation(SOME_DERIVATION)
                .withValue(SOME_VALUE)
                .withOptional(true)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertEquals(SOME_DERIVATION, result.getDerivation());
        assertEquals(SOME_VALUE, result.getValue());
        assertEquals("true", result.getOptional());
    }

}
