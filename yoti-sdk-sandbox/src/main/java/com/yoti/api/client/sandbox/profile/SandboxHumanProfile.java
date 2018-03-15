package com.yoti.api.client.sandbox.profile;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.sandbox.profile.request.attribute.derivation.AgeVerification;

public class SandboxHumanProfile {

    private String familyName;

    private String givenNames;

    private String fullName;

    private AgeVerification ageVerification;

    private Date dateOfBirth;

    private HumanProfile.Gender gender;

    private String postalAddress;

    private String structuredPostalAddress;

    private String nationality;

    private String phoneNumber;

    private String emailAddress;

    private byte[] selfie;

    private DocumentDetails documentDetails;

    SandboxHumanProfile(String familyName,
            String givenNames,
            String fullName,
            AgeVerification ageVerification,
            Date dateOfBirth,
            HumanProfile.Gender gender,
            String postalAddress,
            String structuredPostalAddress,
            String nationality,
            String phoneNumber,
            String emailAddress,
            byte[] selfie,
            DocumentDetails documentDetails) {
        this.familyName = familyName;
        this.givenNames = givenNames;
        this.fullName = fullName;
        this.ageVerification = ageVerification;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.postalAddress = postalAddress;
        this.structuredPostalAddress = structuredPostalAddress;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.selfie = selfie;
        this.documentDetails = documentDetails;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenNames() {
        return givenNames;
    }

    public HumanProfile.Gender getGender() {
        return gender;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getStructuredPostalAddress() {
        return structuredPostalAddress;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public byte[]  getSelfie() {
        return selfie;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public AgeVerification getAgeVerification() {
        return ageVerification;
    }

    public DocumentDetails getDocumentDetails() {
        return documentDetails;
    }
}
