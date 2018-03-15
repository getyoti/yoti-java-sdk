package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

import com.yoti.api.client.Date;
import com.yoti.api.client.sandbox.profile.request.attribute.Attribute;
import com.yoti.api.client.sandbox.profile.request.attribute.ProfileAttributeName;

public class AgeVerification {

    private Date dateOfBirth;
    private AgeDerivationSupported ageDerivationSupported;
    private String ageCheckOption;

    public AgeVerification(Date dateOfBirth, AgeDerivationSupported ageDerivationSupported, String ageCheckOption) {
        this.dateOfBirth = dateOfBirth;
        this.ageDerivationSupported = ageDerivationSupported;
        this.ageCheckOption = ageCheckOption;
    }

    public Attribute buildAttribute() {

        return Attribute.AttributeBuilder.builder()
                .name(ProfileAttributeName.DATE_OF_BIRTH.getValue())
                .value(dateOfBirth.toString())
                .derivation(String.format("%s%s", ageDerivationSupported.getValue(), ageCheckOption)).build();
    }
}
