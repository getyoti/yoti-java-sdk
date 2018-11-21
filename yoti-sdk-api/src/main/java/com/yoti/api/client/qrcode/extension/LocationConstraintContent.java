package com.yoti.api.client.qrcode.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationConstraintContent {

    private final DeviceLocation expectedDeviceLocation;

    LocationConstraintContent(double latitude, double longitude, double radius, double maxUncertainty) {
        this.expectedDeviceLocation = new DeviceLocation(latitude, longitude, radius, maxUncertainty);
    }

    @JsonProperty("expected_device_location")
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

        /**
         * Latitude of the user's expected location
         */
        @JsonProperty("latitude")
        public double getLatitude() {
            return latitude;
        }

        /**
         * Longitude of the user's expected location
         */
        @JsonProperty("longitude")
        public double getLongitude() {
            return longitude;
        }

        /**
         * Acceptable distance from the given lat/long position, in metres
         */
        @JsonProperty("radius")
        public double getRadius() {
            return radius;
        }

        /**
         * Maximum acceptable margin of error, in metres
         */
        @JsonProperty("max_uncertainty_radius")
        public double getMaxUncertainty() {
            return maxUncertainty;
        }
    }

}
