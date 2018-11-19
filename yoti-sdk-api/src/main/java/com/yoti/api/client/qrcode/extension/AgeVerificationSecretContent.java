package com.yoti.api.client.qrcode.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgeVerificationSecretContent {

    private final String secretType;
    private final String secretValue;

    AgeVerificationSecretContent(String secretType, String secretValue) {
        this.secretType = secretType;
        this.secretValue = secretValue;
    }

    @JsonProperty("secret_type")
    public String getSecretType() {
        return secretType;
    }

    @JsonProperty("secret_value")
    public String getSecretValue() {
        return secretValue;
    }

}
