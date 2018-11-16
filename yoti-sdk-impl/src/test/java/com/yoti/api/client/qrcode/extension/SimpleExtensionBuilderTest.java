package com.yoti.api.client.qrcode.extension;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SimpleExtensionBuilderTest {

    private static final String SOME_TYPE = "someType";
    private static final HashMap<String, String> SOME_CONTENT = new HashMap<>();
    private static final double SOME_LATITUDE = 1d;
    private static final double SOME_LONGITUDE = 2d;
    private static final double SOME_RADIUS = 3d;
    private static final double SOME_UNCERTAINTY = 4d;
    private static final String SOME_SECRET = "someSecret";

    static {
        SOME_CONTENT.put("someKey", "someValue");
    }

    @Test
    public void buildsWithTypeAndContent() {
        Extension extension = new SimpleExtensionBuilder()
                .withType(SOME_TYPE)
                .withContent(SOME_CONTENT)
                .build();

        assertEquals(SOME_TYPE, extension.getType());
        assertEquals(SOME_CONTENT, extension.getContent());
    }

    @Test
    public void forLocationConstraint_failsForLatitudeTooLow() {
        try {
            new SimpleExtensionBuilder()
                    .forLocationConstraint(-91, 0, 0, 0)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '-90.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forLocationConstraint_failsForLatitudeTooHigh() {
        try {
            new SimpleExtensionBuilder()
                    .forLocationConstraint(91, 0, 0, 0)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("greater than '90.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forLocationConstraint_failsForLongitudeTooLow() {
        try {
            new SimpleExtensionBuilder()
                    .forLocationConstraint(0, -181, 0, 0)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '-180.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forLocationConstraint_failsForLongitudeTooHigh() {
        try {
            new SimpleExtensionBuilder()
                    .forLocationConstraint(0, 181, 0, 0)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("greater than '180.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forLocationConstraint_failsForRadiusLessThanZero() {
        try {
            new SimpleExtensionBuilder()
                    .forLocationConstraint(0, 0, -1, 0)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '0.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forLocationConstraint_failsForUncertaintyThanZero() {
        try {
            new SimpleExtensionBuilder()
                    .forLocationConstraint(0, 0, 0, -1)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '0.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forLocationConstraint_buildsLocationConstraint() {
        Extension extension = new SimpleExtensionBuilder()
                .forLocationConstraint(SOME_LATITUDE, SOME_LONGITUDE, SOME_RADIUS, SOME_UNCERTAINTY)
                .build();

        assertEquals("LOCATION_CONSTRAINT", extension.getType());
        Map<String, Double> location = (Map<String, Double>) extension.getContent().get("expected_device_location");
        assertThat(location, hasEntry("latitude", SOME_LATITUDE));
        assertThat(location, hasEntry("longitude", SOME_LONGITUDE));
        assertThat(location, hasEntry("radius", SOME_RADIUS));
        assertThat(location, hasEntry("max_uncertainty_radius", SOME_UNCERTAINTY));
    }

    @Test
    public void forTransactionalFlow_failsForNullContent() {
        try {
            new SimpleExtensionBuilder()
                    .forTransactionalFlow(null)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("'content' must not be null"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forTransactionalFlow_buildsWithContent() {
        Map<String, Integer> content = new HashMap<>();
        Extension extension = new SimpleExtensionBuilder()
                .forTransactionalFlow(content)
                .build();

        assertEquals("TRANSACTIONAL_FLOW", extension.getType());
        assertSame(content, extension.getContent());
    }

    @Test
    public void forAgeSecret_failsForEmptySecret() {
        try {
            new SimpleExtensionBuilder()
                    .forAgeSecret("")
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("'secretValue' must not be empty or null"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void forAgeSecret_buildsAgeSecret() {
        Extension extension = new SimpleExtensionBuilder()
                .forAgeSecret(SOME_SECRET)
                .build();

        assertEquals("AGE_VERIFICATION_SECRET", extension.getType());
        assertThat((Map<String, String>) extension.getContent(), hasEntry("secret_type", "EMBEDDED"));
        assertThat((Map<String, String>) extension.getContent(), hasEntry("secret_value", SOME_SECRET));
    }

}
