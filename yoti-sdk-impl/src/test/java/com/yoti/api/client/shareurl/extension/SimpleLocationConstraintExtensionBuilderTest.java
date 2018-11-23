package com.yoti.api.client.shareurl.extension;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SimpleLocationConstraintExtensionBuilderTest {

    private static final double SOME_LATITUDE = 1d;
    private static final double SOME_LONGITUDE = 2d;
    private static final double SOME_RADIUS = 3d;
    private static final double SOME_UNCERTAINTY = 4d;

    @Test
    public void shouldFailForLatitudeTooLow() {
        try {
            new SimpleLocationConstraintExtensionBuilder()
                    .withLatitude(-91)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '-90.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldFailForLatitudeTooHigh() {
        try {
            new SimpleLocationConstraintExtensionBuilder()
                    .withLatitude(91)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("greater than '90.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldFailForLongitudeTooLow() {
        try {
            new SimpleLocationConstraintExtensionBuilder()
                    .withLongitude(-181)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '-180.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldFailForLongitudeTooHigh() {
        try {
            new SimpleLocationConstraintExtensionBuilder()
                    .withLongitude(181)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("greater than '180.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldFailForRadiusLessThanZero() {
        try {
            new SimpleLocationConstraintExtensionBuilder()
                    .withRadius(-1)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '0.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldFailForUncertaintyLessThanZero() {
        try {
            new SimpleLocationConstraintExtensionBuilder()
                    .withMaxUncertainty(-1)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("less than '0.0'"));
            return;
        }
        fail("expected an Exception");
    }

    @Test
    public void shouldBuildLocationConstraintWithGivenValues() {
        Extension<LocationConstraintContent> extension = new SimpleLocationConstraintExtensionBuilder()
                .withLatitude(SOME_LATITUDE)
                .withLongitude(SOME_LONGITUDE)
                .withRadius(SOME_RADIUS)
                .withMaxUncertainty(SOME_UNCERTAINTY)
                .build();

        assertEquals(ExtensionConstants.LOCATION_CONSTRAINT, extension.getType());
        LocationConstraintContent.DeviceLocation expectedDeviceLocation = extension.getContent().getExpectedDeviceLocation();
        assertEquals(SOME_LATITUDE, expectedDeviceLocation.getLatitude(), 0d);
        assertEquals(SOME_LONGITUDE, expectedDeviceLocation.getLongitude(), 0d);
        assertEquals(SOME_RADIUS, expectedDeviceLocation.getRadius(), 0d);
        assertEquals(SOME_UNCERTAINTY, expectedDeviceLocation.getMaxUncertainty(), 0d);
    }

    @Test
    public void shouldBuildLocationConstraintWithDefaultValues() {
        Extension<LocationConstraintContent> extension = new SimpleLocationConstraintExtensionBuilder()
                .withLatitude(SOME_LATITUDE)
                .withLongitude(SOME_LONGITUDE)
                .build();

        assertEquals(ExtensionConstants.LOCATION_CONSTRAINT, extension.getType());
        LocationConstraintContent.DeviceLocation expectedDeviceLocation = extension.getContent().getExpectedDeviceLocation();
        assertEquals(SOME_LATITUDE, expectedDeviceLocation.getLatitude(), 0d);
        assertEquals(SOME_LONGITUDE, expectedDeviceLocation.getLongitude(), 0d);
        assertEquals(SOME_RADIUS, expectedDeviceLocation.getRadius(), 150d);
        assertEquals(SOME_UNCERTAINTY, expectedDeviceLocation.getMaxUncertainty(), 150d);
    }

}
