package com.yoti.api.client.shareurl.extension;

public interface LocationConstraintExtensionBuilder extends ExtensionBuilder<LocationConstraintContent> {

    /**
     * Allows you to specify the Latitude of the user's expected location
     *
     * @return this LocationConstraintExtensionBuilder
     */
    LocationConstraintExtensionBuilder withLatitude(double latitude);

    /**
     * Allows you to specify the Longitude of the user's expected location
     *
     * @return this LocationConstraintExtensionBuilder
     */
    LocationConstraintExtensionBuilder withLongitude(double longitude);

    /**
     * Radius of the circle, centred on the specified location coordinates, where the device is allowed to perform the share
     * If not provided, a default value of 150m will be used.
     *
     * @param radius The allowable distance, in metres, from the given lat/long location
     *
     * @return this LocationConstraintExtensionBuilder
     */
    LocationConstraintExtensionBuilder withRadius(double radius);

    /**
     * Maximum acceptable distance, in metres, of the area of uncertainty associated with the device location coordinates
     * If not provided, a default value of 150m will be used.
     *
     * @param maxUncertainty Maximum allowed measurement uncertainty, in metres
     *
     * @return this LocationConstraintExtensionBuilder
     */
    LocationConstraintExtensionBuilder withMaxUncertainty(double maxUncertainty);

}
