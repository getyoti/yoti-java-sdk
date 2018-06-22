package com.yoti.api.client;

import java.util.Map;

/**
 * Profile of an human user with convenience methods to access well-known attributes.
 */
public interface HumanProfile extends Profile {

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
     * Did the user pass the age verification check?
     *
     * @return <code>TRUE</code> if they passed, <code>FALSE</code> if they failed, <code>null</code> if there was no check
     */
    Boolean isAgeVerified();

    /**
     * Corresponds to the gender in the passport; will be one of the strings "MALE", "FEMALE" or "OTHER".
     *
     * @return the gender
     */
    Attribute<Gender> getGender();

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
    
    static enum Gender {
        MALE, FEMALE, OTHER
    }

}
