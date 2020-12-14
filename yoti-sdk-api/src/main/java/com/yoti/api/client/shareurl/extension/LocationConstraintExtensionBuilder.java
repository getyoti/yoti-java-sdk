package com.yoti.api.client.shareurl.extension;

import com.yoti.api.client.spi.remote.util.Validation;

public class LocationConstraintExtensionBuilder implements ExtensionBuilder<LocationConstraintContent> {

    private double latitude;
    private double longitude;
    private double radius = 150d;
    private double maxUncertainty = 150d;

    /**
     * Allows you to specify the Latitude of the user's expected location
     *
     * @return this LocationConstraintExtensionBuilder
     */
    public LocationConstraintExtensionBuilder withLatitude(double latitude) {
        Validation.withinRange(latitude, -90d, 90d, "latitude");
        this.latitude = latitude;
        return this;
    }

    /**
     * Allows you to specify the Longitude of the user's expected location
     *
     * @return this LocationConstraintExtensionBuilder
     */
    public LocationConstraintExtensionBuilder withLongitude(double longitude) {
        Validation.withinRange(longitude, -180d, 180d, "longitude");
        this.longitude = longitude;
        return this;
    }

    /**
     * Radius of the circle, centred on the specified location coordinates, where the device is allowed to perform the share
     * If not provided, a default value of 150m will be used.
     *
     * @param radius The allowable distance, in metres, from the given lat/long location
     *
     * @return this LocationConstraintExtensionBuilder
     */
    public LocationConstraintExtensionBuilder withRadius(double radius) {
        Validation.notLessThan(radius, 0d, "radius");
        this.radius = radius;
        return this;
    }

    /**
     * Maximum acceptable distance, in metres, of the area of uncertainty associated with the device location coordinates
     * If not provided, a default value of 150m will be used.
     *
     * @param maxUncertainty Maximum allowed measurement uncertainty, in metres
     *
     * @return this LocationConstraintExtensionBuilder
     */
    public LocationConstraintExtensionBuilder withMaxUncertainty(double maxUncertainty) {
        Validation.notLessThan(maxUncertainty, 0d, "maxUncertainty");
        this.maxUncertainty = maxUncertainty;
        return this;
    }

    @Override
    public Extension<LocationConstraintContent> build() {
        LocationConstraintContent content = new LocationConstraintContent(latitude, longitude, radius, maxUncertainty);
        return new Extension(ExtensionConstants.LOCATION_CONSTRAINT, content);
    }

}
