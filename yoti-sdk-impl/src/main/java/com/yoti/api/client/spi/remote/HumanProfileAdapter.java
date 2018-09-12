package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.parseBoolean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;
import com.yoti.api.client.AgeVerification;
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
    private List<AgeVerification> ageVerifications;

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
    public <T> List<Attribute<T>> findAttributesStartingWith(String name, Class<T> clazz) {
        return wrapped.findAttributesStartingWith(name, clazz);
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
        return wrapped.getAttribute(HumanProfileAttributes.FAMILY_NAME, String.class);
    }

    @Override
    public Attribute<String> getGivenNames() {
        return wrapped.getAttribute(HumanProfileAttributes.GIVEN_NAMES, String.class);
    }

    @Override
    public Attribute<String> getFullName() {
        return wrapped.getAttribute(HumanProfileAttributes.FULL_NAME, String.class);
    }

    @Override
    public Attribute<Date> getDateOfBirth() {
        return wrapped.getAttribute(HumanProfileAttributes.DATE_OF_BIRTH, Date.class);
    }

    @Override
    public List<AgeVerification> getAgeVerifications() {
        findAllAgeVerifications();
        return ageVerifications;
    }

    @Override
    public AgeVerification findAgeOverVerification(int age) {
        return findAgeVerification(HumanProfileAttributes.AGE_OVER.replace(":", ""), age);
    }

    @Override
    public AgeVerification findAgeUnderVerification(int age) {
        return findAgeVerification(HumanProfileAttributes.AGE_UNDER.replace(":", ""), age);
    }

    private AgeVerification findAgeVerification(String checkPerformed, int age) {
        findAllAgeVerifications();
        for (AgeVerification ageVerification : ageVerifications) {
            if (ageVerification.getAgeVerified() == age && ageVerification.getCheckPerformed().equals(checkPerformed)) {
                return ageVerification;
            }
        }
        return null;
    }

    private void findAllAgeVerifications() {
        if (ageVerifications == null) {
            List<AgeVerification> verifications = new ArrayList<>();
            for (Attribute<String> ageOver : wrapped.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)) {
                verifications.add(new SimpleAgeVerification(ageOver));
            }
            for (Attribute<String> ageUnder : wrapped.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)) {
                verifications.add(new SimpleAgeVerification(ageUnder));
            }
            ageVerifications = Collections.unmodifiableList(verifications);
        }
    }

    @Override
    @Deprecated
    public Boolean isAgeVerified() {
        Boolean isAgeOver = parseFromStringAttribute(wrapped.findAttributeStartingWith(HumanProfileAttributes.AGE_OVER, String.class));
        Boolean isAgeUnder = parseFromStringAttribute(wrapped.findAttributeStartingWith(HumanProfileAttributes.AGE_UNDER, String.class));
        return isAgeOver != null ? isAgeOver : isAgeUnder;
    }

    @Override
    public Attribute<String> getGender() {
        return wrapped.getAttribute(HumanProfileAttributes.GENDER, String.class);
    }

    @Override
    public Attribute<String> getPostalAddress() {
        return wrapped.getAttribute(HumanProfileAttributes.POSTAL_ADDRESS, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Attribute<Map<?, ?>> getStructuredPostalAddress() {
        return wrapped.getAttribute(HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS, (Class) Map.class);
    }

    @Override
    public Attribute<String> getNationality() {
        return wrapped.getAttribute(HumanProfileAttributes.NATIONALITY, String.class);
    }

    @Override
    public Attribute<String> getPhoneNumber() {
        return wrapped.getAttribute(HumanProfileAttributes.PHONE_NUMBER, String.class);
    }

    @Override
    public Attribute<Image> getSelfie() {
        return wrapped.getAttribute(HumanProfileAttributes.SELFIE, Image.class);
    }

    @Override
    public Attribute<String> getEmailAddress() {
        return wrapped.getAttribute(HumanProfileAttributes.EMAIL_ADDRESS, String.class);
    }

    @Override
    public Attribute<DocumentDetails> getDocumentDetails() {
        return wrapped.getAttribute(HumanProfileAttributes.DOCUMENT_DETAILS, DocumentDetails.class);
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
