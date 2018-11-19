package com.yoti.api.client.qrcode.extension;

import com.yoti.api.client.spi.remote.util.Validation;

public class SimpleAgeVerificationSecretExtensionBuilder implements AgeVerificationSecretExtensionBuilder {

    private String secretType = "EMBEDDED";
    private String secretValue;

    @Override
    public AgeVerificationSecretExtensionBuilder withSecretValue(String secretValue) {
        Validation.notNullOrEmpty(secretValue, "secretValue");
        this.secretValue = secretValue;
        return this;
    }

    @Override
    public Extension<AgeVerificationSecretContent> build() {
        AgeVerificationSecretContent content = new AgeVerificationSecretContent(secretType, secretValue);
        return new SimpleExtension(ExtensionConstants.AGE_VERIFICATION_SECRET, content);
    }

}
