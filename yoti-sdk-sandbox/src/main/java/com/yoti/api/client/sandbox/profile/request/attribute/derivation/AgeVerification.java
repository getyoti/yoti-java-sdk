package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.Date;
import com.yoti.api.client.sandbox.profile.request.attribute.SandboxAttribute;
import com.yoti.api.client.spi.remote.DateValue;
import com.yoti.api.client.spi.remote.util.Validation;

public class AgeVerification {

    private final Date dateOfBirth;
    private final String supportedAgeDerivation;

    private AgeVerification(Date dateOfBirth, String supportedAgeDerivation) {
        this.dateOfBirth = dateOfBirth;
        this.supportedAgeDerivation = supportedAgeDerivation;
    }

    public SandboxAttribute toAttribute() {
        return SandboxAttribute.builder()
                .name(AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH)
                .value(dateOfBirth.toString())
                .derivation(supportedAgeDerivation)
                .build();
    }
    
    public static AgeVerificationBuilder builder() {
        return new AgeVerificationBuilder();
    }

    public static class AgeVerificationBuilder {

        private Date dateOfBirth;
        private String derivation;

        public AgeVerificationBuilder withDateOfBirth(String value) {
            try {
                this.dateOfBirth = DateValue.parseFrom(value);
                return this;
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        public AgeVerificationBuilder withDateOfBirth(Date value) {
            Validation.notNull(value, "dateOfBirth");
            this.dateOfBirth = value;
            return this;
        }

        public AgeVerificationBuilder withAgeOver(int value) {
            return withDerivation(AttributeConstants.HumanProfileAttributes.AGE_OVER + value);
        }

        public AgeVerificationBuilder withAgeUnder(int value) {
            return withDerivation(AttributeConstants.HumanProfileAttributes.AGE_UNDER + value);
        }

        public AgeVerificationBuilder withDerivation(String value) {
            Validation.notNullOrEmpty(value, "derivation");
            this.derivation = value;
            return this;
        }

        public AgeVerification build() {
            if (dateOfBirth == null) {
                throw new IllegalStateException("'dateOfBirth' may not be null");
            }
            if (Validation.isNullOrEmpty(derivation)) {
                throw new IllegalStateException("'derivation' may not be null or empty");
            }

            return new AgeVerification(dateOfBirth, derivation);
        }

    }
    
}
