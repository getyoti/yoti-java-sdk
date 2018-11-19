package com.yoti.api.client.qrcode.extension;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SimpleAgeVerificationSecretExtensionBuilderTest {

    private static final String SOME_SECRET = "someSecret";

    @Test
    public void shouldFailForEmptySecret() {
        try {
            new SimpleAgeVerificationSecretExtensionBuilder()
                    .withSecretValue("")
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("'secretValue' must not be empty or null"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldBuildAgeSecret() {
        Extension<AgeVerificationSecretContent> extension = new SimpleAgeVerificationSecretExtensionBuilder()
                .withSecretValue(SOME_SECRET)
                .build();

        AgeVerificationSecretContent ageVerificationSecretContent = extension.getContent();

        assertEquals("AGE_VERIFICATION_SECRET", extension.getType());
        assertEquals("EMBEDDED", ageVerificationSecretContent.getSecretType());
        assertEquals(SOME_SECRET, ageVerificationSecretContent.getSecretValue());
    }

}
