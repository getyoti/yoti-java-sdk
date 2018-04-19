package com.yoti.api.client;

import java.util.Map;
import java.util.Set;

/**
 * Profile of an human user with convenience methods to access well-known attributes.
 */
public interface HumanProfile extends Profile {

    /**
     * Gets the source types for the specified attribute
     * 
     * @param name the name of the attribute to return the source document types for
     * @return the source (document or system) types for the specified attribute. Returns null if the 
     *            attribute is not in 
     *            the profile.
     */
    Set<String> getAttributeSources(String name);
    
    /**
     * Gets the verifier types for the specified attribute
     * 
     * @param name the name of the attribute to return the verifier document types for
     * @return the verifier(document or system) types for the specified attribute. Returns null if the 
     *            attribute is not in 
     *            the profile.
     */
    Set<String> getAttributeVerifiers(String name);
    
    /**
     * Corresponds to primary name in passport, and surname in English.
     *
     * @return the surname
     */
    String getFamilyName();
    
    /**
     * @return the source types for the family name attribute
     */
    Set<String> getFamilyNameSources();
    
    /**
     * @return the verifier types for the family name attribute
     */
    Set<String> getFamilyNameVerifiers();

    /**
     * Corresponds to secondary names in passport, and first/middle names in English.
     *
     * @return the name
     */
    String getGivenNames();
    
    /**
     * @return the source types for the given names attribute
     */
    Set<String> getGivenNamesSources();

    /**
     * @return the verifier types for the given names attribute
     */
    Set<String> getGivenNamesVerifiers();

    /**
     * The full name attribute.
     *
     * @return the full name
     */
    String getFullName();
    
    /**
     * @return the source types for the full name attribute
     */
    Set<String> getFullNameSources();

    /**
     * @return the verifier types for the full name attribute
     */
    Set<String> getFullNameVerifiers();

    
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
     * @return the source document types for the date of birth attribute
     */
    Set<String> getDateOfBirthSources();
    
    /**
     * @return the verifier types for the date of birth attribute
     */
    Set<String> getDateOfBirthVerifiers();

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
     * @return the source types for the gender attribute
     */
    Set<String> getGenderSources();
    
    /**
     * @return the verifier types for the gender attribute
     */
    Set<String> getGenderVerifiers();
    
    /**
     * The user's postal address as a String
     *
     * @return the postal address
     */
    String getPostalAddress();
    
    /**
     * @return the source types for the postal address attribute
     */
    Set<String> getPostalAddressSources();
    
    /**
     * @return the verifier types for the postal address attribute
     */
    Set<String> getPostalAddressVerifier();
    
    /**
     * The user's structured postal address as a Json
     *
     * @return the postal address
     */
    Map<?, ?> getStructuredPostalAddress();
    
    /**
     * @return the source types for the structured postal address attribute
     */
    Set<String> getStructuredPostalAddressSources();
    
    /**
     * @return the verifier types for the structured postal address attribute
     */
    Set<String> getStructuredPostalAddressVerifiers();
    
    /**
     * Corresponds to the nationality in the passport.
     *
     * @return the nationality
     */
    String getNationality();
    
    /**
     * @return the source types for the nationality attribute
     */
    Set<String> getNationalitySources();

    /**
     * @return the verifier types for the nationality attribute
     */
    Set<String> getNationalityVerifiers();

    /**
     * The user's phone number, as verified at registration time. This will be a number with + for international prefix
     * and no spaces, e.g. "+447777123456".
     *
     * @return the phone number
     */
    String getPhoneNumber();

    /**
     * @return the source types for the phone number attribute
     */
    Set<String> getPhoneNumberSources();

    /**
     * @return the verifier types for the phone number attribute
     */
    Set<String> getPhoneNumberVerifiers();
    
    /**
     * Photograph of user, encoded as a JPEG image.
     *
     * @return the selfie
     */
    Image getSelfie();
    
    /**
     * @return the source types for the selfie attribute
     */
    Set<String> getSelfieSources();

    /**
     * @return the verifier types for the selfie attribute
     */
    Set<String> getSelfieVerifiers();

    /**
     * The user's verified email address.
     *
     * @return the email address
     */
    String getEmailAddress();
    
    /**
     * @return the source types for the email address attribute
     */
    Set<String> getEmailAddressSources();

    /**
     * @return the verifier types for the email address attribute
     */
    Set<String> getEmailAddressVerifiers();

    /**
     * Document details
     *
     * @return the document details
     */
    DocumentDetails getDocumentDetails();
    
    /**
     * @return the source types for the document details attribute
     */
    Set<String> getDocumentDetailsSources();

    /**
     * @return the verifier types for the document details attribute
     */
    Set<String> getDocumentDetailsVerifiers();

    static enum Gender {
        MALE, FEMALE, OTHER
    }

}
