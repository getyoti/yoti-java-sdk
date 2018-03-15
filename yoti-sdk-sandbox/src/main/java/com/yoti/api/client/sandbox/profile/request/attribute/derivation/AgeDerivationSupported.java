package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

public enum AgeDerivationSupported {

    AGE_UNDER("age_under:"),
    AGE_OVER("age_over:");

    private String value;

    AgeDerivationSupported(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
