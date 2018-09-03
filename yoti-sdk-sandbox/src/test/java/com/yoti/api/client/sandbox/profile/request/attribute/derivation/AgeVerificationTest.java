package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;
import com.yoti.api.client.sandbox.profile.request.attribute.SandboxAttribute;
import com.yoti.api.client.spi.remote.DateValue;

import org.hamcrest.core.StringContains;
import org.junit.Test;

public class AgeVerificationTest {

    private static final String VALID_DATE_STRING = "1980-08-05";

    @Test
    public void shouldErrorForBadDateOfBirth() {
        try {
            AgeVerification.builder()
                    .withDateOfBirth("2011-15-35")
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("Unparseable date: \"2011-15-35\""));
            assertTrue(e.getCause() instanceof ParseException);
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldErrorForMissingDateOfBirth() {
        try {
            AgeVerification.builder().build();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), containsString("'dateOfBirth' may not be null"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldErrorForMissingDerivation() {
        try {
            AgeVerification.builder()
                    .withDateOfBirth(VALID_DATE_STRING)
                    .build();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), containsString("'derivation' may not be null or empty"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldParseDateOfBirthSuccessfully() {
        AgeVerification result = AgeVerification.builder()
                .withDateOfBirth(VALID_DATE_STRING)
                .withAgeOver(7)
                .build();

        assertEquals(VALID_DATE_STRING, result.toAttribute().getValue());
    }

    @Test
    public void shouldCreateAgeOverSandboxAttribute() throws Exception {
        SandboxAttribute result = AgeVerification.builder()
                .withDateOfBirth(DateValue.parseFrom(VALID_DATE_STRING))
                .withAgeOver(21)
                .build()
                .toAttribute();

        assertEquals(HumanProfileAttributes.DATE_OF_BIRTH, result.getName());
        assertEquals(VALID_DATE_STRING, result.getValue());
        assertEquals(HumanProfileAttributes.AGE_OVER + 21, result.getDerivation());
        assertEquals("false", result.getOptional());
    }

    @Test
    public void shouldCreateAgeUnderSandboxAttribute() throws Exception {
        SandboxAttribute result = AgeVerification.builder()
                .withDateOfBirth(DateValue.parseFrom(VALID_DATE_STRING))
                .withAgeUnder(16)
                .build()
                .toAttribute();

        assertEquals(HumanProfileAttributes.DATE_OF_BIRTH, result.getName());
        assertEquals(VALID_DATE_STRING, result.getValue());
        assertEquals(HumanProfileAttributes.AGE_UNDER + 16, result.getDerivation());
        assertEquals("false", result.getOptional());
    }

}
