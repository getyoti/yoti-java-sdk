package com.yoti.api.client.docs.session.instructions.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty("latitude")
    private final Double latitude;

    @JsonProperty("longitude")
    private final Double longitude;

    private Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Location.Builder builder() {
        return new Location.Builder();
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public static class Builder {

        private Double latitude;
        private Double longitude;

        private Builder() {}

        /**
         * Sets the latitude of the location, in decimal degrees (e.g. -40.3992)
         *
         * @param latitude the latitude
         * @return the builder
         */
        public Builder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        /**
         * Sets the longitude of the location, in decimal degrees (e.g. 20.4821)
         *
         * @param longitude the longitude
         * @return the builder
         */
        public Builder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(latitude, longitude);
        }

    }

}
