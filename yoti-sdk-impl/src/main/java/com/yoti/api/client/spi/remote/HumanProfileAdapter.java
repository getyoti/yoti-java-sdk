package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.parseBoolean;

import java.util.Collection;
import java.util.Map;

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
    static final String ATTRIBUTE_GENDER = "gender";
    private static final String ATTRIBUTE_POSTAL_ADDRESS = "postal_address";
    private static final String ATTRIBUTE_STRUCTURED_POSTAL_ADDRESS = "structured_postal_address";
    private static final String ATTRIBUTE_NATIONALITY = "nationality";
    private static final String ATTRIBUTE_PHONE_NUMBER = "phone_number";
    private static final String ATTRIBUTE_SELFIE = "selfie";
    private static final String ATTRIBUTE_EMAIL_ADDRESS = "email_address";
    static final String ATTRIBUTE_DOCUMENT_DETAILS = "document_details";

    private final Profile wrapped;

    private HumanProfileAdapter(Profile wrapped) {
        this.wrapped = wrapped;
    }

    public static HumanProfile wrap(Profile wrapped) {
        return new HumanProfileAdapter(wrapped);
    }

    @Override
    public Attribute getAttribute(String name) {
        return wrapped.getAttribute(name);
    }

    @Override
    public <T> Attribute<T> getAttribute(String name, Class<T> clazz) {
        return wrapped.getAttribute(name, clazz);
    }

    @Override
    public <T> Attribute<T> findAttributeStartingWith(String name, Class<T> clazz) {
        return wrapped.findAttributeStartingWith(name, clazz);
    }

    @Override
    public Collection<Attribute<?>> getAttributes() {
        return wrapped.getAttributes();
    }

    @Override
    public Attribute<String> getFamilyName() {
        return wrapped.getAttribute(ATTRIBUTE_FAMILY_NAME, String.class);
    }

    @Override
    public Attribute<String> getGivenNames() {
        return wrapped.getAttribute(ATTRIBUTE_GIVEN_NAMES, String.class);
    }

    @Override
    public Attribute<String> getFullName() {
        return wrapped.getAttribute(ATTRIBUTE_FULL_NAME, String.class);
    }

    @Override
    public Attribute<Date> getDateOfBirth() {
        return wrapped.getAttribute(ATTRIBUTE_DOB, Date.class);
    }

    @Override
    public Boolean isAgeVerified() {
        Boolean isAgeOver = parseFromStringAttribute(wrapped.findAttributeStartingWith(ATTRIBUTE_AGE_OVER, String.class));
        Boolean isAgeUnder = parseFromStringAttribute(wrapped.findAttributeStartingWith(ATTRIBUTE_AGE_UNDER, String.class));
        return isAgeOver != null ? isAgeOver : isAgeUnder;
    }

    @Override
    public Attribute<Gender> getGender() {
        return wrapped.getAttribute(ATTRIBUTE_GENDER, Gender.class);
    }

    @Override
    public Attribute<String> getPostalAddress() {
        return wrapped.getAttribute(ATTRIBUTE_POSTAL_ADDRESS, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Attribute<Map<?, ?>> getStructuredPostalAddress() {
        return wrapped.getAttribute(ATTRIBUTE_STRUCTURED_POSTAL_ADDRESS, (Class) Map.class);
    }

    @Override
    public Attribute<String> getNationality() {
        return wrapped.getAttribute(ATTRIBUTE_NATIONALITY, String.class);
    }

    @Override
    public Attribute<String> getPhoneNumber() {
        return wrapped.getAttribute(ATTRIBUTE_PHONE_NUMBER, String.class);
    }

    @Override
    public Attribute<Image> getSelfie() {
        return wrapped.getAttribute(ATTRIBUTE_SELFIE, Image.class);
    }

    @Override
    public Attribute<String> getEmailAddress() {
        return wrapped.getAttribute(ATTRIBUTE_EMAIL_ADDRESS, String.class);
    }

    @Override
    public Attribute<DocumentDetails> getDocumentDetails() {
        return wrapped.getAttribute(ATTRIBUTE_DOCUMENT_DETAILS, DocumentDetails.class);
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

    private Boolean parseFromStringAttribute(Attribute<String> attribute) {
        return attribute == null ? null : parseBoolean(attribute.getValue());
    }

}
