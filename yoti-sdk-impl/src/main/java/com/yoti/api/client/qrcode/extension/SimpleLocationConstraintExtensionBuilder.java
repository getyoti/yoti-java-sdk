package com.yoti.api.client.qrcode.extension;

import com.yoti.api.client.spi.remote.util.Validation;

public class SimpleLocationConstraintExtensionBuilder implements LocationConstraintExtensionBuilder {

    private double latitude;
    private double longitude;
    private double radius;
    private double maxUncertainty;

    @Override
    public LocationConstraintExtensionBuilder withLatitude(double latitude) {
        Validation.withinRange(latitude, -90d, 90d, "latitude");
        this.latitude = latitude;
        return this;
    }

    @Override
    public LocationConstraintExtensionBuilder withLongitude(double longitude) {
        Validation.withinRange(longitude, -180d, 180d, "longitude");
        this.longitude = longitude;
        return this;
    }

    @Override
    public LocationConstraintExtensionBuilder withRadius(double radius) {
        Validation.notLessThan(radius, 0d, "radius");
        this.radius = radius;
        return this;
    }

    @Override
    public LocationConstraintExtensionBuilder withMaxUncertainty(double maxUncertainty) {
        Validation.notLessThan(maxUncertainty, 0d, "maxUncertainty");
        this.maxUncertainty = maxUncertainty;
        return this;
    }

    @Override
    public Extension<LocationConstraintContent> build() {
        LocationConstraintContent content = new LocationConstraintContent(latitude, longitude, radius, maxUncertainty);
        return new SimpleExtension(ExtensionConstants.LOCATION_CONSTRAINT, content);
    }

}
