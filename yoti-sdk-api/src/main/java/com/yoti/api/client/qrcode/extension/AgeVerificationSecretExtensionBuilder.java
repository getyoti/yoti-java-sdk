package com.yoti.api.client.qrcode.extension;

public interface AgeVerificationSecretExtensionBuilder extends ExtensionBuilder<AgeVerificationSecretContent> {

    AgeVerificationSecretExtensionBuilder withSecretValue(String value);

}
