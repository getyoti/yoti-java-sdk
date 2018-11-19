package com.yoti.api.client.qrcode.extension;

public interface LocationConstraintExtensionBuilder extends ExtensionBuilder<LocationConstraintContent> {

    LocationConstraintExtensionBuilder withLatitude(double value);

    LocationConstraintExtensionBuilder withLongitude(double value);

    LocationConstraintExtensionBuilder withRadius(double value);

    LocationConstraintExtensionBuilder withMaxUncertainty(double value);

}
