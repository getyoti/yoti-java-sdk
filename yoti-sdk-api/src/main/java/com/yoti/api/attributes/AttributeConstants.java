package com.yoti.api.attributes;

public final class AttributeConstants {

    private AttributeConstants() {

    }

    public static final class HumanProfileAttributes {

        public static final String FAMILY_NAME = "family_name";
        public static final String GIVEN_NAMES = "given_names";
        public static final String FULL_NAME = "full_name";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String AGE_OVER = "age_over:";
        public static final String AGE_UNDER = "age_under:";
        public static final String GENDER = "gender";
        public static final String POSTAL_ADDRESS = "postal_address";
        public static final String STRUCTURED_POSTAL_ADDRESS = "structured_postal_address";
        public static final String NATIONALITY = "nationality";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String SELFIE = "selfie";
        public static final String EMAIL_ADDRESS = "email_address";
        public static final String DOCUMENT_DETAILS = "document_details";

        public static final class Keys {

            public static final String FORMATTED_ADDRESS = "formatted_address";
        }

    }

    public static final class ApplicationProfileAttributes {

        public static final String ATTRIBUTE_APPLICATION_NAME = "application_name";
        public static final String ATTRIBUTE_APPLICATION_LOGO = "application_logo";
        public static final String ATTRIBUTE_APPLICATION_URL = "application_url";
        public static final String ATTRIBUTE_APPLICATION_RECEIPT_BGCOLOR = "application_receipt_bgcolor";
    }

}
