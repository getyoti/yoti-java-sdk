package com.yoti.api.client;

import java.util.Map;

/**
 * Profile of an human user with convenience methods to access well-known attributes.
 */
public interface HumanProfile extends Profile {
    /**
     * Corresponds to primary name in passport, and surname in English.
     *
     * @return the surname
     */
    String getFamilyName();

    /**
     * Corresponds to secondary names in passport, and first/middle names in English.
     *
     * @return the name
     */
    String getGivenNames();

    /**
     * Equal to ${given_names} + " " + ${family_name}.
     *
     * @return the given names + the surname
     * @deprecated this method has never featured as intended (it has always returned null). Deprecated since version 1.2, likely to be removed in version 2.0. Instead please use {@link #getGivenAndLastNames()}
     */
    @Deprecated
    String getFullName();

    /**
     * Equal to ${given_names} + " " + ${family_name}.
     *
     * @return the given names + the surname
     * @since 1.2
     */
    String getGivenAndLastNames();

    /**
     * Date of birth
     *
     * @return Date of birth
     */
    Date getDateOfBirth();

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
     * The user's postal address as a String
     *
     * @return the postal address
     */
    String getPostalAddress();
    
    /**
     * The user's structured postal address as a Json
     *
     * @return the postal address
     */
    Map<?, ?> getStructuredPostalAddress();
    
    /**
     * Corresponds to the nationality in the passport.
     *
     * @return the nationality
     */
    String getNationality();

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
