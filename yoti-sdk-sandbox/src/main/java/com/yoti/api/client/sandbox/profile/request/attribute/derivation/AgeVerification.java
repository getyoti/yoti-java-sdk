package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

import com.yoti.api.client.Date;
import com.yoti.api.client.sandbox.profile.request.attribute.Attribute;
import com.yoti.api.client.sandbox.profile.request.attribute.ProfileAttributeName;

public class AgeVerification {

    private Date dateOfBirth;
    private SupportedAgeDerivation supportedAgeDerivation;
    private String ageCheckOption;

    public AgeVerification(Date dateOfBirth, SupportedAgeDerivation supportedAgeDerivation, String ageCheckOption) {
        this.dateOfBirth = dateOfBirth;
        this.supportedAgeDerivation = supportedAgeDerivation;
        this.ageCheckOption = ageCheckOption;
    }

    public Attribute buildAttribute() {

        return Attribute.AttributeBuilder.builder()
                .name(ProfileAttributeName.DATE_OF_BIRTH.getValue())
                .value(dateOfBirth.toString())
                .derivation(String.format("%s%s", supportedAgeDerivation.getValue(), ageCheckOption)).build();
    }
}
