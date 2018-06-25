package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.parseBoolean;

import java.util.Collection;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants;
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
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.FAMILY_NAME, String.class);
    }

    @Override
    public Attribute<String> getGivenNames() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.GIVEN_NAMES, String.class);
    }

    @Override
    public Attribute<String> getFullName() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.FULL_NAME, String.class);
    }

    @Override
    public Attribute<Date> getDateOfBirth() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH, Date.class);
    }

    @Override
    public Boolean isAgeVerified() {
        Boolean isAgeOver = parseFromStringAttribute(wrapped.findAttributeStartingWith(AttributeConstants.HumanProfileAttributes.AGE_OVER, String.class));
        Boolean isAgeUnder = parseFromStringAttribute(wrapped.findAttributeStartingWith(AttributeConstants.HumanProfileAttributes.AGE_UNDER, String.class));
        return isAgeOver != null ? isAgeOver : isAgeUnder;
    }

    @Override
    public Attribute<String> getGender() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.GENDER, String.class);
    }

    @Override
    public Attribute<String> getPostalAddress() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Attribute<Map<?, ?>> getStructuredPostalAddress() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS, (Class) Map.class);
    }

    @Override
    public Attribute<String> getNationality() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.NATIONALITY, String.class);
    }

    @Override
    public Attribute<String> getPhoneNumber() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.PHONE_NUMBER, String.class);
    }

    @Override
    public Attribute<Image> getSelfie() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.SELFIE, Image.class);
    }

    @Override
    public Attribute<String> getEmailAddress() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.EMAIL_ADDRESS, String.class);
    }

    @Override
    public Attribute<DocumentDetails> getDocumentDetails() {
        return wrapped.getAttribute(AttributeConstants.HumanProfileAttributes.DOCUMENT_DETAILS, DocumentDetails.class);
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
