package com.yoti.api.client.shareurl.extension;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SimpleTransactionalFlowExtensionBuilderTest {

    private static final Object SOME_OBJECT = new Object();

    @Test
    public void shouldFailForNullContent() {
        try {
            new SimpleTransactionalFlowExtensionBuilder()
                    .withContent(null)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("'content' must not be null"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldBuildWithContent() {
        Extension extension = new SimpleTransactionalFlowExtensionBuilder()
                .withContent(SOME_OBJECT)
                .build();

        assertEquals(ExtensionConstants.TRANSACTIONAL_FLOW, extension.getType());
        assertSame(SOME_OBJECT, extension.getContent());
    }

}
