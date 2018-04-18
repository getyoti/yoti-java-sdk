package com.yoti.api.client.sandbox.profile;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.sandbox.profile.request.attribute.derivation.AgeVerification;

public class SandboxHumanProfileBuilder {

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

    public static SandboxHumanProfileBuilder newInstance() {
        return new SandboxHumanProfileBuilder();
    }

    public SandboxHumanProfileBuilder familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public SandboxHumanProfileBuilder givenNames(String givenNames) {
        this.givenNames = givenNames;
        return this;
    }

    public SandboxHumanProfileBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public SandboxHumanProfileBuilder ageVerification(AgeVerification ageVerification) {
        this.ageVerification = ageVerification;
        return this;
    }

    public SandboxHumanProfileBuilder dateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public SandboxHumanProfileBuilder gender(HumanProfile.Gender gender) {
        this.gender = gender;
        return this;
    }

    public SandboxHumanProfileBuilder postalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public SandboxHumanProfileBuilder structuredPostalAddress(String structuredPostalAddress) {
        this.structuredPostalAddress = structuredPostalAddress;
        return this;
    }

    public SandboxHumanProfileBuilder nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public SandboxHumanProfileBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SandboxHumanProfileBuilder emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public SandboxHumanProfileBuilder selfie(byte[]  selfie) {
        this.selfie = selfie;
        return this;
    }

    public SandboxHumanProfileBuilder documentDetails(DocumentDetails documentDetails) {
        this.documentDetails = documentDetails;
        return this;
    }

    public SandboxHumanProfile build() {
        return new SandboxHumanProfile(familyName,
                givenNames,
                fullName,
                ageVerification,
                dateOfBirth,
                gender,
                postalAddress,
                structuredPostalAddress,
                nationality,
                phoneNumber,
                emailAddress,
                selfie,
                documentDetails);
    }

}
