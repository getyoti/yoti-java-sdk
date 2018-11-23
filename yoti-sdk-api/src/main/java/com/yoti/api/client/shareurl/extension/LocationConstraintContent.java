package com.yoti.api.client.shareurl.extension;

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
         * Radius of the circle, centred on the specified location coordinates, where the device is allowed to perform the share
         */
        @JsonProperty("radius")
        public double getRadius() {
            return radius;
        }

        /**
         * Maximum acceptable distance, in metres, of the area of uncertainty associated with the device location coordinates
         */
        @JsonProperty("max_uncertainty_radius")
        public double getMaxUncertainty() {
            return maxUncertainty;
        }
    }

}
