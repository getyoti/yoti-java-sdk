package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.yoti.api.client.Attribute;

import org.hamcrest.core.StringContains;
import org.junit.Test;

public class SimpleAgeVerificationTest {

    @Test
    public void shouldFailForAttributeNamesThatDontMatchThePattern() {
        assertExceptionForMalformedAttributeName("");
        assertExceptionForMalformedAttributeName(":");
        assertExceptionForMalformedAttributeName(":18");
        assertExceptionForMalformedAttributeName("age_over:");
        assertExceptionForMalformedAttributeName("age_over:not_int");
        assertExceptionForMalformedAttributeName(":age_over:18");
        assertExceptionForMalformedAttributeName("age_over::18");
        assertExceptionForMalformedAttributeName("age_over:18:");
        assertExceptionForMalformedAttributeName("age_over:18:21");
    }

    private static void assertExceptionForMalformedAttributeName(String attName) {
        try {
            Attribute<String> attribute = new SimpleAttribute<>(attName, null);
            new SimpleAgeVerification(attribute);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), StringContains.containsString("attribute.name"));
            assertThat(e.getMessage(), StringContains.containsString(attName));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldParseWellFormedAgeDerivation() {
        Attribute<String> attribute = new SimpleAttribute<>("any_string_here:21", "true");

        SimpleAgeVerification result = new SimpleAgeVerification(attribute);

        assertEquals(result.getCheckPerformed(), "any_string_here");
        assertEquals(result.getAgeVerified(), 21);
        assertEquals(result.getResult(), true);
    }

}
