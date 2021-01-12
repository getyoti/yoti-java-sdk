package com.yoti.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AgeVerificationTest {

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

    private void assertExceptionForMalformedAttributeName(String attName) {
        try {
            Attribute<String> attribute = new Attribute<>(attName, null);
            new AgeVerification(attribute);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("attribute.name"));
            assertThat(e.getMessage(), containsString(attName));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldParseWellFormedAgeDerivation() {
        Attribute<String> attribute = new Attribute<>("any_string_here:21", "true");

        AgeVerification result = new AgeVerification(attribute);

        assertEquals("any_string_here", result.getCheckType());
        assertEquals(21, result.getAge());
        assertTrue(result.getResult());
    }

}
