package com.yoti.api.examples.springboot.attribute;

import java.util.EnumSet;
import java.util.Optional;

import com.yoti.api.client.Attribute;

enum AttributeDisplayProperty {

    FAMILY_NAME("family_name", "Family name", "yoti-icon-profile"),
    GIVEN_NAMES("given_names", "Given names", "yoti-icon-profile"),
    FULL_NAME("full_name", "Full name", "yoti-icon-profile"),
    DATE_OF_BIRTH("date_of_birth", "Date of birth", "yoti-icon-calendar"),
    GENDER("gender", "Gender", "yoti-icon-gender"),
    POSTAL_ADDRESS("postal_address", "Address", "yoti-icon-address"),
    STRUCTURED_POSTAL_ADDRESS("structured_postal_address", "Structured address", "yoti-icon-address"),
    NATIONALITY("nationality", "Nationality", "yoti-icon-nationality"),
    PHONE_NUMBER("phone_number", "Mobile number", "yoti-icon-phone"),
    SELFIE("selfie"),
    EMAIL_ADDRESS("email_address", "Email address", "yoti-icon-email"),
    IDENTITY_PROFILE_REPORT("identity_profile_report", "Identity Profile Report", "yoti-icon-document", true);

    private final String name;
    private final String label;
    private final String icon;
    private final boolean isJson;

    AttributeDisplayProperty(String name) {
        this.name = name;
        label = null;
        icon = null;
        isJson = false;
    }

    AttributeDisplayProperty(String name, String label, String icon) {
        this.name = name;
        this.label = label;
        this.icon = icon;
        isJson = false;
    }

    AttributeDisplayProperty(String name, String label, String icon, boolean isJson) {
        this.name = name;
        this.label = label;
        this.icon = icon;
        this.isJson = isJson;
    }

    public static Optional<AttributeDisplayProperty> fromAttribute(Attribute<?> attribute) {
        return EnumSet.allOf(AttributeDisplayProperty.class).stream()
                .filter(adp -> adp.name.equals(attribute.getName()))
                .findFirst();
    }

    public String label() {
        return label;
    }

    public String icon() {
        return icon;
    }

    public boolean isJson() {
        return isJson;
    }

}
