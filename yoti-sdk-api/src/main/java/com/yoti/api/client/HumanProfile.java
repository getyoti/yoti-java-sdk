package com.yoti.api.client;

import java.util.Map;

/**
 * Profile of an human user with convenience methods to access well-known attributes.
 */
public interface HumanProfile extends Profile {

    /**
     * Gets the source document the for the specified attribute
     * 
     * @param name the name of the attribute to return the srouce document type for
     * @return the source document type for the specified attribute. Returns null if the 
     *            attribute does not come from a document or if the attribute is not in 
     *            the profile.
     */
    String getAttributeSource(String name);
    
    /**
     * Corresponds to primary name in passport, and surname in English.
     *
     * @return the surname
     */
    String getFamilyName();
    
    /**
     * @return the source document type for the family name attribute
     */
    String getFamilyNameSource();

    /**
     * Corresponds to secondary names in passport, and first/middle names in English.
     *
     * @return the name
     */
    String getGivenNames();
    
    /**
     * @return the source document type for the given names attribute
     */
    String getGivenNamesSource();

    /**
     * The full name attribute.
     *
     * @return the full name
     */
    String getFullName();
    
    /**
     * @return the source document type for the full name attribute
     */
    String getFullNameSource();

    /**
     * Equal to ${given_names} + " " + ${family_name}.
     *
     * @return the given names + the surname
     * @since 1.2
     * @deprecated
     */
    @Deprecated
    String getGivenAndLastNames();

    /**
     * Date of birth
     *
     * @return Date of birth
     */
    Date getDateOfBirth();
    
    /**
     * @return the source document type for the date of birth attribute
     */
    String getDateOfBirthSource();

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
    Gender getGender();

    /**
     * @return the source document type for the gender attribute
     */
    String getGenderSource();
    
    /**
     * The user's postal address as a String
     *
     * @return the postal address
     */
    String getPostalAddress();
    
    /**
     * @return the source document type for the postal address attribute
     */
    String getPostalAddressSource();
    
    /**
     * The user's structured postal address as a Json
     *
     * @return the postal address
     */
    Map<?, ?> getStructuredPostalAddress();
    
    /**
     * @return the source document type for the structured postal address attribute
     */
    String getStructuredPostalAddressSource();
    
    /**
     * Corresponds to the nationality in the passport.
     *
     * @return the nationality
     */
    String getNationality();
    
    /**
     * @return the source document type for the nationality attribute
     */
    String getNationalitySource();

    /**
     * The user's phone number, as verified at registration time. This will be a number with + for international prefix
     * and no spaces, e.g. "+447777123456".
     *
     * @return the phone number
     */
    String getPhoneNumber();

    /**
     * Photograph of user, encoded as a JPEG image.
     *
     * @return the selfie
     */
    Image getSelfie();

    /**
     * The user's verified email address.
     *
     * @return the email address
     */
    String getEmailAddress();

    /**
     * Document details
     *
     * @return the document details
     */
    DocumentDetails getDocumentDetails();

    static enum Gender {
        MALE, FEMALE, OTHER
    }

}
