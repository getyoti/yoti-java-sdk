package com.yoti.api.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants;

public class HumanProfile extends Profile {

    public static final String GENDER_MALE = "MALE";
    public static final String GENDER_FEMALE = "FEMALE";
    public static final String GENDER_TRANSGENDER = "TRANSGENDER";
    public static final String GENDER_OTHER = "OTHER";

    private Map<String, AgeVerification> verificationsMap;

    /**
     * Create a new Human profile based on a list of attributes
     *
     * @param attributeList list containing the attributes for this profile
     */
    public HumanProfile(List<Attribute<?>> attributeList) {
        super(attributeList);
    }

    /**
     * Corresponds to primary name in passport, and surname in English.
     *
     * @return the family name
     */
    public Attribute<String> getFamilyName() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.FAMILY_NAME, String.class);
    }

    /**
     * Corresponds to secondary names in passport, and first/middle names in English.
     *
     * @return the given names
     */
    public Attribute<String> getGivenNames() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.GIVEN_NAMES, String.class);
    }

    /**
     * The full name attribute.
     *
     * @return the full name
     */
    public Attribute<String> getFullName() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.FULL_NAME, String.class);
    }

    /**
     * Date of birth
     *
     * @return Date of birth
     */
    public Attribute<Date> getDateOfBirth() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH, Date.class);
    }

    /**
     * Finds all the 'Age Over' and 'Age Under' derived attributes returned with the profile, and returns them wrapped in {@link AgeVerification}
     * objects
     *
     * @return A list of all the {@link AgeVerification} objects returned for this profile
     */
    public List<AgeVerification> getAgeVerifications() {
        findAllAgeVerifications();
        return Collections.unmodifiableList(new ArrayList<>(verificationsMap.values()));
    }

    /**
     * Searches for an {@link AgeVerification} corresponding to an 'Age Over' check for the given age
     *
     * @param age the age the search over
     * @return age_over {@link AgeVerification} for the given age, or <code>null</code> if no match was found
     */
    public AgeVerification findAgeOverVerification(int age) {
        findAllAgeVerifications();
        return verificationsMap.get(AttributeConstants.HumanProfileAttributes.AGE_OVER + age);
    }

    /**
     * Searches for an {@link AgeVerification} corresponding to an 'Age Under' check for the given age
     *
     * @param age the age to search under
     * @return age_under {@link AgeVerification} for the given age, or <code>null</code> if no match was found
     */
    public AgeVerification findAgeUnderVerification(int age) {
        findAllAgeVerifications();
        return verificationsMap.get(AttributeConstants.HumanProfileAttributes.AGE_UNDER + age);
    }

    private void findAllAgeVerifications() {
        if (verificationsMap == null) {
            Map<String, AgeVerification> verifications = new HashMap<>();
            for (Attribute<String> ageOver : findAttributesStartingWith(AttributeConstants.HumanProfileAttributes.AGE_OVER, String.class)) {
                verifications.put(ageOver.getName(), new AgeVerification(ageOver));
            }
            for (Attribute<String> ageUnder : findAttributesStartingWith(AttributeConstants.HumanProfileAttributes.AGE_UNDER, String.class)) {
                verifications.put(ageUnder.getName(), new AgeVerification(ageUnder));
            }
            this.verificationsMap = verifications;
        }
    }

    /**
     * Corresponds to the gender in the passport; will be one of the strings "MALE", "FEMALE", "TRANSGENDER" or "OTHER".
     *
     * @return the gender
     */
    public Attribute<String> getGender() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.GENDER, String.class);
    }

    /**
     * The user's postal address as a String
     *
     * @return the postal address
     */
    public Attribute<String> getPostalAddress() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS, String.class);
    }

    /**
     * The user's structured postal address as a Json
     *
     * @return the postal address
     */
    @SuppressWarnings("unchecked")
    public Attribute<Map<?, ?>> getStructuredPostalAddress() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS, (Class) Map.class);
    }

    /**
     * Corresponds to the nationality in the passport.
     *
     * @return the nationality
     */
    public Attribute<String> getNationality() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.NATIONALITY, String.class);
    }

    /**
     * The user's phone number, as verified at registration time. This will be a number with + for international prefix
     * and no spaces, e.g. "+447777123456".
     *
     * @return the phone number
     */
    public Attribute<String> getPhoneNumber() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.PHONE_NUMBER, String.class);
    }

    /**
     * Photograph of user, encoded as a JPEG image.
     *
     * @return the selfie
     */
    public Attribute<Image> getSelfie() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.SELFIE, Image.class);
    }

    /**
     * The user's verified email address.
     *
     * @return the email address
     */
    public Attribute<String> getEmailAddress() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.EMAIL_ADDRESS, String.class);
    }

    /**
     * Document details
     *
     * @return the document details
     */
    public Attribute<DocumentDetails> getDocumentDetails() {
        return getAttribute(AttributeConstants.HumanProfileAttributes.DOCUMENT_DETAILS, DocumentDetails.class);
    }

    /**
     * Document images
     *
     * @return images of the document
     */
    @SuppressWarnings("unchecked")
    public Attribute<List<Image>> getDocumentImages() {
        Attribute<?> a = getAttribute(AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES, List.class);
        return (Attribute<List<Image>>) a;
    }

}
