package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

public enum SupportedAgeDerivation {

    AGE_UNDER("age_under:"),
    AGE_OVER("age_over:");

    private String value;

    SupportedAgeDerivation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
