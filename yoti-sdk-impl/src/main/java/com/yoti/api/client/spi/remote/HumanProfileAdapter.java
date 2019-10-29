package com.yoti.api.client.spi.remote;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;
import com.yoti.api.client.Date;
import com.yoti.api.client.*;

import java.util.*;

import static java.lang.Boolean.parseBoolean;

/**
 * Adapter linking Profile and ApplicationProfile together by wrapping the latter and exposing well-known attributes.
 */
final class HumanProfileAdapter implements HumanProfile {

    private final Profile wrapped;
    private Map<String, AgeVerification> verificationsMap;

    private HumanProfileAdapter(Profile wrapped) {
        this.wrapped = wrapped;
    }

    public static HumanProfile wrap(Profile wrapped) {
        return new HumanProfileAdapter(wrapped);
    }

    @Override
    @Deprecated
    public Attribute getAttribute(String name) {
        return wrapped.getAttribute(name);
    }

    @Override
    @Deprecated
    public <T> Attribute<T> getAttribute(String name, Class<T> clazz) {
        return wrapped.getAttribute(name, clazz);
    }

    /**
     * Return single typed {@link Attribute} object
     * by exact name
     *
     * @param name  the name of the {@link Attribute}
     * @param clazz the type of the {@link Attribute} value
     * @return typed attribute, null if it is not present in the profile
     */
    @Override
    public <T> Attribute<T> getAttributeByName(String name, Class<T> clazz) {
        return wrapped.getAttributeByName(name, clazz);
    }

    /**
     * Return single {@link Attribute} object
     * by exact name
     *
     * @param name the name of the {@link Attribute}
     * @return the attribute object, null if it is not present in the profule
     */
    @Override
    public Attribute getAttributeByName(String name) {
        return wrapped.getAttributeByName(name);
    }

    /**
     * Return a list of {@link Attribute}s that match
     * the exact name
     *
     * @param name  the name of the {@link Attribute}s
     * @param clazz the type of the {@link Attribute} value
     * @return typed list of attribute, empty list if there are no matching attributes on the profile
     */
    @Override
    public <T> List<Attribute<T>> getAttributesByName(String name, Class<T> clazz) {
        return wrapped.getAttributesByName(name, clazz);
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
        return wrapped.getAttributeByName(HumanProfileAttributes.FAMILY_NAME, String.class);
    }

    @Override
    public Attribute<String> getGivenNames() {
        return wrapped.getAttributeByName(HumanProfileAttributes.GIVEN_NAMES, String.class);
    }

    @Override
    public Attribute<String> getFullName() {
        return wrapped.getAttributeByName(HumanProfileAttributes.FULL_NAME, String.class);
    }

    @Override
    public Attribute<Date> getDateOfBirth() {
        return wrapped.getAttributeByName(HumanProfileAttributes.DATE_OF_BIRTH, Date.class);
    }

    @Override
    public List<AgeVerification> getAgeVerifications() {
        findAllAgeVerifications();
        return Collections.unmodifiableList(new ArrayList<>(verificationsMap.values()));
    }

    @Override
    public AgeVerification findAgeOverVerification(int age) {
        findAllAgeVerifications();
        return verificationsMap.get(HumanProfileAttributes.AGE_OVER + age);
    }

    @Override
    public AgeVerification findAgeUnderVerification(int age) {
        findAllAgeVerifications();
        return verificationsMap.get(HumanProfileAttributes.AGE_UNDER + age);
    }

    private void findAllAgeVerifications() {
        if (verificationsMap == null) {
            Map<String, AgeVerification> verifications = new HashMap<>();
            for (Attribute<String> ageOver : wrapped.findAttributesStartingWith(HumanProfileAttributes.AGE_OVER, String.class)) {
                verifications.put(ageOver.getName(), new SimpleAgeVerification(ageOver));
            }
            for (Attribute<String> ageUnder : wrapped.findAttributesStartingWith(HumanProfileAttributes.AGE_UNDER, String.class)) {
                verifications.put(ageUnder.getName(), new SimpleAgeVerification(ageUnder));
            }
            this.verificationsMap = verifications;
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
        return wrapped.getAttributeByName(HumanProfileAttributes.GENDER, String.class);
    }

    @Override
    public Attribute<String> getPostalAddress() {
        return wrapped.getAttributeByName(HumanProfileAttributes.POSTAL_ADDRESS, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Attribute<Map<?, ?>> getStructuredPostalAddress() {
        return wrapped.getAttributeByName(HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS, (Class) Map.class);
    }

    @Override
    public Attribute<String> getNationality() {
        return wrapped.getAttributeByName(HumanProfileAttributes.NATIONALITY, String.class);
    }

    @Override
    public Attribute<String> getPhoneNumber() {
        return wrapped.getAttributeByName(HumanProfileAttributes.PHONE_NUMBER, String.class);
    }

    @Override
    public Attribute<Image> getSelfie() {
        return wrapped.getAttributeByName(HumanProfileAttributes.SELFIE, Image.class);
    }

    @Override
    public Attribute<String> getEmailAddress() {
        return wrapped.getAttributeByName(HumanProfileAttributes.EMAIL_ADDRESS, String.class);
    }

    @Override
    public Attribute<DocumentDetails> getDocumentDetails() {
        return wrapped.getAttributeByName(HumanProfileAttributes.DOCUMENT_DETAILS, DocumentDetails.class);
    }

    @Override
    public Attribute<List<Image>> getDocumentImages() {
        Attribute<?> a = wrapped.getAttributeByName(HumanProfileAttributes.DOCUMENT_IMAGES, List.class);
        return (Attribute<List<Image>>) a;
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
