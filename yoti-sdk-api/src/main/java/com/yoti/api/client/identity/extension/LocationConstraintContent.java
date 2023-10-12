package com.yoti.api.client.identity.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationConstraintContent {

    private final DeviceLocation expectedDeviceLocation;

    LocationConstraintContent(double latitude, double longitude, double radius, double maxUncertainty) {
        this.expectedDeviceLocation = new DeviceLocation(latitude, longitude, radius, maxUncertainty);
    }

    @JsonProperty(Property.EXPECTED_DEVICE_LOCATION)
    public DeviceLocation getExpectedDeviceLocation() {
        return expectedDeviceLocation;
    }

    public static final class DeviceLocation {

        private final double latitude;
        private final double longitude;
        private final double radius;
        private final double maxUncertainty;

        DeviceLocation(double latitude, double longitude, double radius, double maxUncertainty) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.maxUncertainty = maxUncertainty;
        }

        @JsonProperty(Property.LATITUDE)
        public double getLatitude() {
            return latitude;
        }

        @JsonProperty(Property.LONGITUDE)
        public double getLongitude() {
            return longitude;
        }

        @JsonProperty(Property.RADIUS)
        public double getRadius() {
            return radius;
        }

        @JsonProperty(Property.MAX_UNCERTAINTY_RADIUS)
        public double getMaxUncertainty() {
            return maxUncertainty;
        }

    }

    private static final class Property {

        private static final String EXPECTED_DEVICE_LOCATION = "expected_device_location";
        private static final String LATITUDE = "latitude";
        private static final String LONGITUDE = "longitude";
        private static final String RADIUS = "radius";
        private static final String MAX_UNCERTAINTY_RADIUS = "max_uncertainty_radius";

    }

}
