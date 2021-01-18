package com.yoti.api.client.shareurl.policy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.shareurl.constraint.Constraint;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Set of data required to request a sharing transaction
 */
public class DynamicPolicy {

    @JsonProperty("wanted")
    private final Collection<WantedAttribute> wantedAttributes;

    @JsonProperty("wanted_auth_types")
    private final Set<Integer> wantedAuthTypes;

    @JsonProperty("wanted_remember_me")
    private final boolean wantedRememberMe;

    @JsonProperty("wanted_remember_me_optional")
    private final boolean wantedRememberMeOptional;

    DynamicPolicy(Collection<WantedAttribute> wantedAttributes,
            Set<Integer> wantedAuthTypes,
            boolean wantedRememberMe,
            boolean wantedRememberMeOptional) {
        this.wantedAttributes = wantedAttributes;
        this.wantedAuthTypes = wantedAuthTypes;
        this.wantedRememberMe = wantedRememberMe;
        this.wantedRememberMeOptional = wantedRememberMeOptional;
    }

    public static DynamicPolicy.Builder builder() {
        return new DynamicPolicy.Builder();
    }

    /**
     * Set of required {@link WantedAttribute}
     *
     * @return attributes
     */
    public Collection<WantedAttribute> getWantedAttributes() {
        return wantedAttributes;
    }

    /**
     * Type of authentications
     *
     * @return authentication types
     */
    public Set<Integer> getWantedAuthTypes() {
        return wantedAuthTypes;
    }

    /**
     * Allows to remember the {@link DynamicPolicy}
     *
     * @return RememberMe
     */
    public boolean isWantedRememberMe() {
        return wantedRememberMe;
    }

    /**
     * Defines the {@link #isWantedRememberMe()} optional for the sharing
     *
     * @return RememberMeOptional
     */
    public boolean isWantedRememberMeOptional() {
        return wantedRememberMeOptional;
    }

    public static class Builder {

        private static final int SELFIE_AUTH_TYPE = 1;
        private static final int PIN_AUTH_TYPE = 2;

        private final Map<String, WantedAttribute> wantedAttributes = new HashMap<>();
        private final Set<Integer> wantedAuthTypes = new HashSet<>();
        private boolean wantedRememberMe;
        private boolean wantedRememberMeOptional;

        public Builder withWantedAttribute(WantedAttribute wantedAttribute) {
            String key = wantedAttribute.getDerivation() != null ? wantedAttribute.getDerivation() : wantedAttribute.getName();

            if (wantedAttribute.getConstraints()
                    .size() > 0) {
                key += "-" + wantedAttribute.getConstraints()
                        .hashCode();
            }

            this.wantedAttributes.put(key, wantedAttribute);
            return this;
        }

        public Builder withWantedAttribute(boolean optional, String name, List<Constraint> constraints) {
            WantedAttribute wantedAttribute = WantedAttribute.builder()
                    .withName(name)
                    .withOptional(optional)
                    .withConstraints(constraints)
                    .build();
            return withWantedAttribute(wantedAttribute);
        }

        public Builder withWantedAttribute(boolean optional, String name) {
            return withWantedAttribute(optional, name, Collections.<Constraint>emptyList());
        }

        public Builder withFamilyName() {
            return withFamilyName(false);
        }

        public Builder withFamilyName(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.FAMILY_NAME);
        }

        public Builder withGivenNames() {
            return withGivenNames(false);
        }

        public Builder withGivenNames(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.GIVEN_NAMES);
        }

        public Builder withFullName() {
            return withFullName(false);
        }

        public Builder withFullName(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.FULL_NAME);
        }

        public Builder withDateOfBirth() {
            return withDateOfBirth(false);
        }

        public Builder withDateOfBirth(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH);
        }

        public Builder withAgeOver(int age) {
            return withAgeOver(false, age);
        }

        public Builder withAgeOver(boolean optional, int age) {
            return withAgeDerivedAttribute(optional, AttributeConstants.HumanProfileAttributes.AGE_OVER + age);
        }

        public Builder withAgeUnder(int age) {
            return withAgeUnder(false, age);
        }
        
        public Builder withAgeUnder(boolean optional, int age) {
            return withAgeDerivedAttribute(optional, AttributeConstants.HumanProfileAttributes.AGE_UNDER + age);
        }

        private Builder withAgeDerivedAttribute(boolean optional, String derivation) {
            WantedAttribute wantedAttribute = WantedAttribute.builder()
                    .withName(AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH)
                    .withDerivation(derivation)
                    .withOptional(optional)
                    .build();
            return withWantedAttribute(wantedAttribute);
        }

        public Builder withGender() {
            return withGender(false);
        }

        public Builder withGender(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.GENDER);
        }

        public Builder withPostalAddress() {
            return withPostalAddress(false);
        }

        public Builder withPostalAddress(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS);
        }

        public Builder withStructuredPostalAddress() {
            return withStructuredPostalAddress(false);
        }

        public Builder withStructuredPostalAddress(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS);
        }

        public Builder withNationality() {
            return withNationality(false);
        }

        public Builder withNationality(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.NATIONALITY);
        }

        public Builder withPhoneNumber() {
            return withPhoneNumber(false);
        }

        public Builder withPhoneNumber(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.PHONE_NUMBER);
        }

        public Builder withSelfie() {
            return withSelfie(false);
        }

        public Builder withSelfie(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.SELFIE);
        }

        public Builder withEmail() {
            return withEmail(false);
        }

        public Builder withEmail(boolean optional) {
            return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.EMAIL_ADDRESS);
        }

        public Builder withSelfieAuthentication(boolean enabled) {
            return this.withWantedAuthType(SELFIE_AUTH_TYPE, enabled);
        }

        public Builder withPinAuthentication(boolean enabled) {
            return this.withWantedAuthType(PIN_AUTH_TYPE, enabled);
        }

        public Builder withWantedAuthType(int wantedAuthType) {
            this.wantedAuthTypes.add(wantedAuthType);
            return this;
        }

        public Builder withWantedAuthType(int wantedAuthType, boolean enabled) {
            if (enabled) {
                return this.withWantedAuthType(wantedAuthType);
            } else {
                this.wantedAuthTypes.remove(wantedAuthType);
                return this;
            }
        }

        public Builder withWantedRememberMe(boolean wantedRememberMe) {
            this.wantedRememberMe = wantedRememberMe;
            return this;
        }

        public Builder withWantedRememberMeOptional(boolean wantedRememberMeOptional) {
            this.wantedRememberMeOptional = wantedRememberMeOptional;
            return this;
        }
        
        public DynamicPolicy build() {
            return new DynamicPolicy(wantedAttributes.values(), wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
        }

    }

}
