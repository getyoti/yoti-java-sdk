package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.parseBoolean;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Image;
import com.yoti.api.client.Profile;

/**
 * Adapter linking Profile and ApplicationProfile together by wrapping the latter and exposing well-known attributes.
 */
final class HumanProfileAdapter implements HumanProfile {

    private static final String ATTRIBUTE_FAMILY_NAME = "family_name";
    private static final String ATTRIBUTE_GIVEN_NAMES = "given_names";
    private static final String ATTRIBUTE_FULL_NAME = "full_name";
    private static final String ATTRIBUTE_DOB = "date_of_birth";
    private static final String ATTRIBUTE_AGE_OVER = "age_over:";
    private static final String ATTRIBUTE_AGE_UNDER = "age_under:";
    private static final String ATTRIBUTE_GENDER = "gender";
    private static final String ATTRIBUTE_POSTAL_ADDRESS = "postal_address";
    private static final String ATTRIBUTE_STRUCTURED_POSTAL_ADDRESS = "structured_postal_address";
    private static final String ATTRIBUTE_NATIONALITY = "nationality";
    private static final String ATTRIBUTE_PHONE_NUMBER = "phone_number";
    private static final String ATTRIBUTE_SELFIE = "selfie";
    private static final String ATTRIBUTE_EMAIL_ADDRESS = "email_address";
    private static final String ATTRIBUTE_DOCUMENT_DETAILS = "document_details";

    private static final char SPACE = ' ';

    private final Profile wrapped;

    private HumanProfileAdapter(Profile wrapped) {
        this.wrapped = wrapped;
    }

    public static HumanProfile wrap(Profile wrapped) {
        return new HumanProfileAdapter(wrapped);
    }

    @Override
    public String getAttribute(String name) {
        return wrapped.getAttribute(name);
    }

    @Override
    public Attribute getAttributeObject(String name) {
       return wrapped.getAttributeObject(name);
    }

    @Override
    public boolean is(String name, boolean defaultValue) {
        return wrapped.is(name, defaultValue);
    }

    @Override
    public <T> T getAttribute(String name, Class<T> clazz) {
        return wrapped.getAttribute(name, clazz);
    }

    @Override
    public <T> T findAttributeStartingWith(String name, Class<T> clazz) {
        return wrapped.findAttributeStartingWith(name, clazz);
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return wrapped.getAttributes();
    }

    @Override
    public String getFamilyName() {
        return wrapped.getAttribute(ATTRIBUTE_FAMILY_NAME);
    }

    @Override
    public String getGivenNames() {
        return wrapped.getAttribute(ATTRIBUTE_GIVEN_NAMES);
    }

    @Override
    public String getFullName() {
        return wrapped.getAttribute(ATTRIBUTE_FULL_NAME);
    }

    @Deprecated
    @Override
    public String getGivenAndLastNames() {
        final String givenNames = getGivenNames();
        final String familyName = getFamilyName();
        if (givenNames == null && familyName == null) {
            return null;
        } else {
            return buildGivenAndLastNameString(givenNames, familyName);
        }
    }

    @Override
    public Date getDateOfBirth() {
        return wrapped.getAttribute(ATTRIBUTE_DOB, Date.class);
    }

    @Override
    public Boolean isAgeVerified() {
        Boolean isAgeOver = parseFromStringValue(wrapped.findAttributeStartingWith(ATTRIBUTE_AGE_OVER, String.class));
        Boolean isAgeUnder = parseFromStringValue(wrapped.findAttributeStartingWith(ATTRIBUTE_AGE_UNDER, String.class));
        return isAgeOver != null ? isAgeOver : isAgeUnder;
    }

    @Override
    public Gender getGender() {
        String genderString = wrapped.getAttribute(ATTRIBUTE_GENDER);
        if (genderString != null) {
            try {
                return Gender.valueOf(genderString);
            } catch (IllegalArgumentException isa) {
                return Gender.OTHER;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getPostalAddress() {
        return wrapped.getAttribute(ATTRIBUTE_POSTAL_ADDRESS);
    }

    @Override
    public Map<?, ?> getStructuredPostalAddress() {
        return wrapped.getAttribute(ATTRIBUTE_STRUCTURED_POSTAL_ADDRESS, Map.class);
    }

    @Override
    public String getNationality() {
        return wrapped.getAttribute(ATTRIBUTE_NATIONALITY);
    }

    @Override
    public String getPhoneNumber() {
        return wrapped.getAttribute(ATTRIBUTE_PHONE_NUMBER);
    }

    @Override
    public Image getSelfie() {
        return wrapped.getAttribute(ATTRIBUTE_SELFIE, Image.class);
    }

    @Override
    public String getEmailAddress() {
        return wrapped.getAttribute(ATTRIBUTE_EMAIL_ADDRESS);
    }

    @Override
    public DocumentDetails getDocumentDetails() {
        try {
            return DocumentDetailsAttributeValue.parseFrom(wrapped.getAttribute(ATTRIBUTE_DOCUMENT_DETAILS));
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wrapped == null) ? 0 : wrapped.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HumanProfileAdapter other = (HumanProfileAdapter) obj;
        if (wrapped == null) {
            if (other.wrapped != null) {
                return false;
            }
        } else if (!wrapped.equals(other.wrapped)) {
            return false;
        }
        return true;
    }

    @Override
    public Set<String> getAttributeSources(String name) {
        Attribute attribute = getAttributeObject(name);
        return (attribute == null) ? null : attribute.getSources();
    }

    @Override
    public Set<String> getFamilyNameSources() {
        return getAttributeSources(ATTRIBUTE_FAMILY_NAME);
    }

    @Override
    public Set<String> getGivenNamesSources() {
        return getAttributeSources(ATTRIBUTE_GIVEN_NAMES);
    }

    @Override
    public Set<String> getFullNameSources() {
        return getAttributeSources(ATTRIBUTE_FULL_NAME);
    }

    @Override
    public Set<String> getDateOfBirthSources() {
        return getAttributeSources(ATTRIBUTE_DOB);
    }

    @Override
    public Set<String> getGenderSources() {
        return getAttributeSources(ATTRIBUTE_GENDER);
    }

    @Override
    public Set<String> getPostalAddressSources() {
        return getAttributeSources(ATTRIBUTE_POSTAL_ADDRESS);
    }

    @Override
    public Set<String> getStructuredPostalAddressSources() {
        return getAttributeSources(ATTRIBUTE_STRUCTURED_POSTAL_ADDRESS);
    }

    @Override
    public Set<String> getNationalitySources() {
        return getAttributeSources(ATTRIBUTE_NATIONALITY);
    }

    @Override
    public Set<String> getPhoneNumberSources() {
        return getAttributeSources(ATTRIBUTE_PHONE_NUMBER);
    }

    @Override
    public Set<String> getSelfieSources() {
        return getAttributeSources(ATTRIBUTE_SELFIE);
    }

    @Override
    public Set<String> getEmailAddressSources() {
        return getAttributeSources(ATTRIBUTE_EMAIL_ADDRESS);
    }

    @Override
    public Set<String> getDocumentDetailsSources() {
        return getAttributeSources(ATTRIBUTE_DOCUMENT_DETAILS);
    }

    @Override
    public Set<String> getAttributeVerifiers(String name) {
        Attribute attribute = getAttributeObject(name);
        return (attribute == null) ? null : attribute.getVerifiers();
    }

    @Override
    public Set<String> getFamilyNameVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_FAMILY_NAME);
    }

    @Override
    public Set<String> getGivenNamesVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_GIVEN_NAMES);
    }

    @Override
    public Set<String> getFullNameVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_FULL_NAME);
    }

    @Override
    public Set<String> getDateOfBirthVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_DOB);
    }

    @Override
    public Set<String> getGenderVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_GENDER);
    }

    @Override
    public Set<String> getPostalAddressVerifier() {
        return getAttributeVerifiers(ATTRIBUTE_POSTAL_ADDRESS);
    }

    @Override
    public Set<String> getStructuredPostalAddressVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_STRUCTURED_POSTAL_ADDRESS);
    }

    @Override
    public Set<String> getNationalityVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_NATIONALITY);
    }

    @Override
    public Set<String> getPhoneNumberVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_PHONE_NUMBER);
    }

    @Override
    public Set<String> getSelfieVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_SELFIE);
    }

    @Override
    public Set<String> getEmailAddressVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_EMAIL_ADDRESS);
    }

    @Override
    public Set<String> getDocumentDetailsVerifiers() {
        return getAttributeVerifiers(ATTRIBUTE_DOCUMENT_DETAILS);
    }

    private void appendIfNotNull(final StringBuilder builder, final String input) {
        if (input != null) {
            builder.append(input);
        }
    }

    private String buildGivenAndLastNameString(final String givenNames, final String familyName) {
        // in Java 8 a java.util.StringJoiner does this much more nicely - but this is JDK 6 compatible.
        final StringBuilder result = new StringBuilder();
        appendIfNotNull(result, givenNames);
        if (givenNames != null && familyName != null) {
            result.append(SPACE);
        }
        appendIfNotNull(result, familyName);
        return result.toString();
    }

    private Boolean parseFromStringValue(String value) {
        return value == null ? null : parseBoolean(value);
    }

}
