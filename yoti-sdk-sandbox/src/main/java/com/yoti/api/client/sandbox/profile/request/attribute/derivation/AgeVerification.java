package com.yoti.api.client.sandbox.profile.request.attribute.derivation;

import java.util.Collections;
import java.util.List;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.Date;
import com.yoti.api.client.sandbox.profile.request.attribute.SandboxAnchor;
import com.yoti.api.client.sandbox.profile.request.attribute.SandboxAttribute;
import com.yoti.api.client.spi.remote.DateValue;
import com.yoti.api.client.spi.remote.util.Validation;

public class AgeVerification {

    private final Date dateOfBirth;
    private final String supportedAgeDerivation;
    private final List<SandboxAnchor> anchors;

    private AgeVerification(Date dateOfBirth, String supportedAgeDerivation, List<SandboxAnchor> anchors) {
        Validation.notNull(dateOfBirth, "dateOfBirth");
        Validation.notNullOrEmpty(supportedAgeDerivation, "derivation");

        this.dateOfBirth = dateOfBirth;
        this.supportedAgeDerivation = supportedAgeDerivation;
        this.anchors = anchors;
    }

    public SandboxAttribute toAttribute() {
        return SandboxAttribute.builder()
                .withName(AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH)
                .withValue(dateOfBirth.toString())
                .withDerivation(supportedAgeDerivation)
                .withAnchors(anchors)
                .build();
    }

    public static AgeVerificationBuilder builder() {
        return new AgeVerificationBuilder();
    }

    public static class AgeVerificationBuilder {

        private Date dateOfBirth;
        private String derivation;
        private List<SandboxAnchor> anchors = Collections.emptyList();

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

        public AgeVerificationBuilder withAnchors(List<SandboxAnchor> anchors) {
            Validation.notNull(anchors, "anchors");
            this.anchors = anchors;
            return this;
        }

        public AgeVerification build() {
            try {
                return new AgeVerification(dateOfBirth, derivation, anchors);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    }

}
