package com.yoti.api.client.sandbox.profile.request.attribute;

public enum ProfileAttributeName {

    GIVEN_NAMES("given_names"),
    FAMILY_NAME("family_name"),
    FULL_NAME("full_name"),
    DATE_OF_BIRTH("date_of_birth"),
    GENDER("gender"),
    POSTAL_ADDRESS("postal_address"),
    STRUCTURED_POSTAL_ADDRESS("structured_postal_address"),
    NATIONALITY("nationality"),
    PHONE_NUMBER("phone_number"),
    SELFIE("selfie"),
    EMAIL_ADDRESS("email_address"),
    DOCUMENT_DETAILS("document_details");

    private String value;

    ProfileAttributeName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
