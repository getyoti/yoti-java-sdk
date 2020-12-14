package com.yoti.api.client.shareurl.extension;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class BasicExtensionBuilderTest {

    private static final String SOME_TYPE = "someType";
    private static final HashMap<String, String> SOME_CONTENT = new HashMap<>();

    static {
        SOME_CONTENT.put("someKey", "someValue");
    }

    @Test
    public void shouldBuildWithTypeAndContent() {
        Extension<?> extension = new BasicExtensionBuilder()
                .withType(SOME_TYPE)
                .withContent(SOME_CONTENT)
                .build();

        assertEquals(SOME_TYPE, extension.getType());
        assertEquals(SOME_CONTENT, extension.getContent());
    }

}
