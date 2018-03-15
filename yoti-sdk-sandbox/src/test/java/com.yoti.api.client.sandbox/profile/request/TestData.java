package com.yoti.api.client.sandbox.profile.request;

import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.sandbox.profile.request.attribute.Attribute;
import com.yoti.api.client.sandbox.profile.request.attribute.ProfileAttributeName;
import com.yoti.api.client.sandbox.profile.request.attribute.SandboxDateAttribute;
import com.yoti.api.client.sandbox.profile.request.attribute.derivation.AgeDerivationSupported;
import com.yoti.api.client.sandbox.profile.request.attribute.derivation.AgeVerification;

import org.bouncycastle.util.encoders.Base64;

class TestData {

    static final String FAMILY_NAME = "Doe";
    static final String GIVEN_NAMES = "John lol";
    static final String EMAIL_ADDRESS = "john@email.com";
    static final String FULL_NAME = "John lol Doe";
    static final String DATE_OF_BIRTH = "1902-02-02";
    static final String NATIONALITY = "InTheMiddleOfNowhere";
    static final String PHONE_NUMBER = "123456789";
    static final String POSTAL_ADDRESS = "Road of the roads";
    static final String SELFIE = "12345678987654321234567876543212345676543211234";

    static final Attribute FAMILY_NAME_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.FAMILY_NAME.getValue())
            .value(FAMILY_NAME)
            .build();

    static final Attribute GIVEN_NAMES_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.GIVEN_NAMES.getValue())
            .value(GIVEN_NAMES)
            .build();

    static final Attribute EMAIL_ADDRESS_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.EMAIL_ADDRESS.getValue())
            .value(EMAIL_ADDRESS)
            .build();

    static final Attribute FULL_NAME_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.FULL_NAME.getValue())
            .value(FULL_NAME)
            .build();

    static final Attribute GENDER_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.GENDER.getValue())
            .value(HumanProfile.Gender.MALE.name())
            .build();

    static final Attribute DATE_OF_BIRTH_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.DATE_OF_BIRTH.getValue())
            .value(DATE_OF_BIRTH)
            .build();

    static final Attribute NATIONALITY_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.NATIONALITY.getValue())
            .value(NATIONALITY)
            .build();

    static final Attribute PHONE_NUMBER_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.PHONE_NUMBER.getValue())
            .value(PHONE_NUMBER)
            .build();

    static final Attribute POSTAL_ADDRESS_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.POSTAL_ADDRESS.getValue())
            .value(POSTAL_ADDRESS)
            .build();

    static final Attribute SELFIE_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.SELFIE.getValue())
            .value(Base64.toBase64String(SELFIE.getBytes()))
            .build();

    static final Attribute DOCUMENT_DETAILS_ATTRIBUTE = Attribute.AttributeBuilder.builder()
            .name(ProfileAttributeName.DOCUMENT_DETAILS.getValue())
            .value("Jhon lol")
            .build();

    static final Attribute AGE_UNDER_ATTRIBUTE = new AgeVerification(new SandboxDateAttribute(2009, 2, 2),
            AgeDerivationSupported.AGE_UNDER, "18").buildAttribute();

    static final Attribute AGE_OVER_ATTRIBUTE = new AgeVerification(new SandboxDateAttribute(1978, 2, 2),
            AgeDerivationSupported.AGE_OVER, "18").buildAttribute();


}
