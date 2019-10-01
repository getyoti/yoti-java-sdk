package com.yoti.api.client;

import java.util.List;
import java.util.Map;

/**
 * Profile of a human user with convenience methods to access well-known attributes.
 */
public interface HumanProfile extends Profile {

    String GENDER_MALE = "MALE";
    String GENDER_FEMALE = "FEMALE";
    String GENDER_TRANSGENDER = "TRANSGENDER";
    String GENDER_OTHER = "OTHER";

    /**
     * Corresponds to primary name in passport, and surname in English.
     *
     * @return the family name
     */
    Attribute<String> getFamilyName();

    /**
     * Corresponds to secondary names in passport, and first/middle names in English.
     *
     * @return the given names
     */
    Attribute<String> getGivenNames();

    /**
     * The full name attribute.
     *
     * @return the full name
     */
    Attribute<String> getFullName();

    /**
     * Date of birth
     *
     * @return Date of birth
     */
    Attribute<Date> getDateOfBirth();

    /**
     * Finds all the 'Age Over' and 'Age Under' derived attributes returned with the profile, and returns them wrapped in {@link AgeVerification}
     * objects
     *
     * @return A list of all the {@link AgeVerification} objects returned for this profile
     */
    List<AgeVerification> getAgeVerifications();

    /**
     * Searches for an {@link AgeVerification} corresponding to an 'Age Over' check for the given age
     *
     *
     * @param age the age the search over
     * @return age_over {@link AgeVerification} for the given age, or <code>null</code> if no match was found
     */
    AgeVerification findAgeOverVerification(int age);

    /**
     * Searches for an {@link AgeVerification} corresponding to an 'Age Under' check for the given age
     *
     * @param age the age to search under
     * @return age_under {@link AgeVerification} for the given age, or <code>null</code> if no match was found
     */
    AgeVerification findAgeUnderVerification(int age);

    /**
     * @deprecated Did the user pass the age verification check?  From SDK 2.1 onwards use getAgeVerifications(), findAgeOverVerification(int age)
     * or findAgeUnderVerification(int age)
     *
     * @return <code>TRUE</code> if they passed, <code>FALSE</code> if they failed, <code>null</code> if there was no check
     */
    @Deprecated
    Boolean isAgeVerified();

    /**
     * Corresponds to the gender in the passport; will be one of the strings "MALE", "FEMALE", "TRANSGENDER" or "OTHER".
     *
     * @return the gender
     */
    Attribute<String> getGender();

    /**
     * The user's postal address as a String
     *
     * @return the postal address
     */
    Attribute<String> getPostalAddress();

    /**
     * The user's structured postal address as a Json
     *
     * @return the postal address
     */
    Attribute<Map<?, ?>> getStructuredPostalAddress();

    /**
     * Corresponds to the nationality in the passport.
     *
     * @return the nationality
     */
    Attribute<String> getNationality();

    /**
     * The user's phone number, as verified at registration time. This will be a number with + for international prefix
     * and no spaces, e.g. "+447777123456".
     *
     * @return the phone number
     */
    Attribute<String> getPhoneNumber();

    /**
     * Photograph of user, encoded as a JPEG image.
     *
     * @return the selfie
     */
    Attribute<Image> getSelfie();

    /**
     * The user's verified email address.
     *
     * @return the email address
     */
    Attribute<String> getEmailAddress();

    /**
     * Document details
     *
     * @return the document details
     */
    Attribute<DocumentDetails> getDocumentDetails();

    /**
     * Document images
     *
     * @return images of the document
     */
    Attribute<List<Image>> getDocumentImages();

}
