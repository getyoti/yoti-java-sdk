package com.yoti.api.client.sandbox.profile.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.sandbox.profile.SandboxHumanProfile;
import com.yoti.api.client.sandbox.profile.request.attribute.Attribute;
import com.yoti.api.client.sandbox.profile.request.attribute.ProfileAttributeName;

import com.google.common.base.Strings;
import org.bouncycastle.util.encoders.Base64;

public class YotiTokenRequestMapper {

    private static final String DOCUMENT_DETAILS_SEPARATOR = " ";

    public static YotiTokenRequest mapSharingTokenRequest(SandboxHumanProfile sandboxHumanProfile, String userId) {

        List<Attribute> attributes = new ArrayList<>();

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getGivenNames())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.GIVEN_NAMES.getValue())
                    .value(sandboxHumanProfile.getGivenNames())
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getFamilyName())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.FAMILY_NAME.getValue())
                    .value(sandboxHumanProfile.getFamilyName())
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getFullName())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.FULL_NAME.getValue())
                    .value(sandboxHumanProfile.getFullName())
                    .build());
        }

        if (sandboxHumanProfile.getDateOfBirth() != null) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.DATE_OF_BIRTH.getValue())
                    .value(sandboxHumanProfile.getDateOfBirth().toString())
                    .build());
        }

        if (sandboxHumanProfile.getGender() != null) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.GENDER.getValue())
                    .value(sandboxHumanProfile.getGender().name())
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getPhoneNumber())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.PHONE_NUMBER.getValue())
                    .value(sandboxHumanProfile.getPhoneNumber())
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getNationality())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.NATIONALITY.getValue())
                    .value(sandboxHumanProfile.getNationality())
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getPostalAddress())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.POSTAL_ADDRESS.getValue())
                    .value(sandboxHumanProfile.getPostalAddress())
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getStructuredPostalAddress())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.STRUCTURED_POSTAL_ADDRESS.getValue())
                    .value(sandboxHumanProfile.getStructuredPostalAddress())
                    .build());
        }

        if (sandboxHumanProfile.getSelfie() != null) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.SELFIE.getValue())
                    .value(Base64.toBase64String(sandboxHumanProfile.getSelfie()))
                    .build());
        }

        if (!Strings.isNullOrEmpty(sandboxHumanProfile.getEmailAddress())) {
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.EMAIL_ADDRESS.getValue())
                    .value(sandboxHumanProfile.getEmailAddress())
                    .build());
        }

        if (sandboxHumanProfile.getAgeVerification() != null) {
            attributes.add(sandboxHumanProfile.getAgeVerification().buildAttribute());
        }

        if (sandboxHumanProfile.getDocumentDetails() != null) {
            DocumentDetails documentDetails = sandboxHumanProfile.getDocumentDetails();
            attributes.add(Attribute.AttributeBuilder.builder()
                    .name(ProfileAttributeName.DOCUMENT_DETAILS.getValue())
                    .optional("true")
                    .value(buildDocumentDetailsAttributeValue(documentDetails)).build());
        }

        return new YotiTokenRequest(userId, Collections.unmodifiableList(attributes));
    }

    private static String buildDocumentDetailsAttributeValue(DocumentDetails documentDetails) {
        String documentDetailsValue = documentDetails.getType().name()
                + DOCUMENT_DETAILS_SEPARATOR
                + documentDetails.getIssuingCountry()
                + DOCUMENT_DETAILS_SEPARATOR
                + documentDetails.getDocumentNumber();

        if (documentDetails.getExpirationDate() != null) {
            documentDetailsValue += DOCUMENT_DETAILS_SEPARATOR + documentDetails.getExpirationDate().toString();
        } else if (documentDetails.getExpirationDate() == null && !Strings.isNullOrEmpty(documentDetails.getIssuingAuthority())) {
            documentDetailsValue += DOCUMENT_DETAILS_SEPARATOR + "-";
        }

        if (!Strings.isNullOrEmpty(documentDetails.getIssuingAuthority())) {
            documentDetailsValue += DOCUMENT_DETAILS_SEPARATOR + documentDetails.getIssuingAuthority();
        }

        return documentDetailsValue;

    }

}
