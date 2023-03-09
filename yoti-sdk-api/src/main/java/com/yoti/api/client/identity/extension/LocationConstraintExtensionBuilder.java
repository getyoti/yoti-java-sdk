package com.yoti.api.client.identity.extension;

import com.yoti.validation.Validation;

public class LocationConstraintExtensionBuilder implements ExtensionBuilder<LocationConstraintContent> {

    private double latitude;
    private double longitude;
    private double radius = 150d;
    private double maxUncertainty = 150d;

    public LocationConstraintExtensionBuilder withLatitude(double latitude) {
        Validation.withinRange(latitude, -90d, 90d, Property.LATITUDE);

        this.latitude = latitude;
        return this;
    }

    public LocationConstraintExtensionBuilder withLongitude(double longitude) {
        Validation.withinRange(longitude, -180d, 180d, Property.LONGITUDE);

        this.longitude = longitude;
        return this;
    }

    public LocationConstraintExtensionBuilder withRadius(double radius) {
        Validation.notLessThan(radius, 0d, Property.RADIUS);

        this.radius = radius;
        return this;
    }

    public LocationConstraintExtensionBuilder withMaxUncertainty(double maxUncertainty) {
        Validation.notLessThan(maxUncertainty, 0d, Property.MAX_UNCERTAINTY);

        this.maxUncertainty = maxUncertainty;
        return this;
    }

    @Override
    public Extension<LocationConstraintContent> build() {
        LocationConstraintContent content = new LocationConstraintContent(latitude, longitude, radius, maxUncertainty);
        return new Extension<>("LOCATION_CONSTRAINT", content);
    }

    private static final class Property {

        private static final String LATITUDE = "latitude";
        private static final String LONGITUDE = "longitude";
        private static final String RADIUS = "radius";
        private static final String MAX_UNCERTAINTY = "maxUncertainty";

    }

}
