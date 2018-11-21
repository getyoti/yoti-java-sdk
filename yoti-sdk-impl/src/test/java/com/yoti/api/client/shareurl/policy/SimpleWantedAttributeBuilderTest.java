package com.yoti.api.client.shareurl.policy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SimpleWantedAttributeBuilderTest {

    public static final String SOME_NAME = "someName";
    public static final String SOME_DERIVATION = "someDerivation";

    @Test
    public void buildsAnAttribute() {
        WantedAttribute result = new SimpleWantedAttributeBuilder()
                .withName(SOME_NAME)
                .withDerivation(SOME_DERIVATION)
                .withOptional(true)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertEquals(SOME_DERIVATION, result.getDerivation());
        assertEquals(true, result.isOptional());
    }

}
