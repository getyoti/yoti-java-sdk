package com.yoti.api.client.identity.policy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.identity.constraint.Constraint;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Policy {

    @JsonProperty(Property.WANTED)
    private final Collection<WantedAttribute> wantedAttributes;

    @JsonProperty(Property.WANTED_AUTH_TYPES)
    private final Set<Integer> wantedAuthTypes;

    @JsonProperty(Property.WANTED_REMEMBER_ME)
    private final boolean wantedRememberMe;

    @JsonProperty(Property.WANTED_REMEMBER_ME_OPTIONAL)
    private final boolean wantedRememberMeOptional;

    @JsonProperty(Property.IDENTITY_PROFILE_REQUIREMENTS)
    private final Object identityProfile;

    @JsonProperty(Property.ADVANCED_IDENTITY_PROFILE_REQUIREMENTS)
    private final Object advancedIdentityProfile;

    private Policy(Builder builder) {
        wantedAttributes = builder.wantedAttributes.values();
        wantedAuthTypes = builder.wantedAuthTypes;
        wantedRememberMe = builder.wantedRememberMe;
        wantedRememberMeOptional = builder.wantedRememberMeOptional;
        identityProfile = builder.identityProfile;
        advancedIdentityProfile = builder.advancedIdentityProfile;
    }

    public Collection<WantedAttribute> getWantedAttributes() {
        return wantedAttributes;
    }

    public Set<Integer> getWantedAuthTypes() {
        return wantedAuthTypes;
    }

    public boolean isWantedRememberMe() {
        return wantedRememberMe;
    }

    public boolean isWantedRememberMeOptional() {
        return wantedRememberMeOptional;
    }

    public Object getIdentityProfile() {
        return identityProfile;
    }

    public Object getAdvancedIdentityProfile() {
        return advancedIdentityProfile;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final int SELFIE_AUTH_TYPE = 1;
        private static final int PIN_AUTH_TYPE = 2;

        private final Map<String, WantedAttribute> wantedAttributes;
        private final Set<Integer> wantedAuthTypes;

        private boolean wantedRememberMe;
        private boolean wantedRememberMeOptional;
        private Object identityProfile;
        private Object advancedIdentityProfile;

        private Builder() {
            wantedAttributes = new HashMap<>();
            wantedAuthTypes = new HashSet<>();
        }

        public Builder withWantedAttribute(WantedAttribute wanted) {
            String key = Optional.ofNullable(wanted.getDerivation()).orElseGet(wanted::getName);

            if (!wanted.hasConstraints()) {
                wantedAttributes.put(key, wanted);
                return this;
            }

            if (!hasDuplicatedConstraints(wantedAttributes, wanted)) {
                wantedAttributes.put(key + "-" + UUID.randomUUID(), wanted);
            }

            return this;
        }

        private static boolean hasDuplicatedConstraints(Map<String, WantedAttribute> attributes, WantedAttribute wanted) {
            return attributes.values().stream()
                    .filter(attribute -> attribute.getName().equals(wanted.getName()))
                    .map(WantedAttribute::getConstraints)
                    .anyMatch(c -> c.equals(wanted.getConstraints()));
        }

        public Builder withWantedAttribute(boolean optional, String name, List<Constraint> constraints) {
            return withWantedAttribute(
                    WantedAttribute.builder()
                            .withName(name)
                            .withOptional(optional)
                            .withConstraints(constraints)
                            .build()
            );
        }

        public Builder withWantedAttribute(boolean optional, String name) {
            return withWantedAttribute(optional, name, Collections.emptyList());
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
            return withWantedAuthType(SELFIE_AUTH_TYPE, enabled);
        }

        public Builder withPinAuthentication(boolean enabled) {
            return withWantedAuthType(PIN_AUTH_TYPE, enabled);
        }

        public Builder withWantedAuthType(int wantedAuthType) {
            this.wantedAuthTypes.add(wantedAuthType);
            return this;
        }

        public Builder withWantedAuthType(int wantedAuthType, boolean enabled) {
            if (enabled) {
                return withWantedAuthType(wantedAuthType);
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

        public Builder withIdentityProfile(Object identityProfile) {
            this.identityProfile = identityProfile;
            return this;
        }

        public Builder withAdvancedIdentityProfile(Object advancedIdentityProfile) {
            this.advancedIdentityProfile = advancedIdentityProfile;
            return this;
        }
        
        public Policy build() {
            return new Policy(this);
        }

    }

    private static final class Property {

        private static final String WANTED = "wanted";
        private static final String WANTED_AUTH_TYPES = "wanted_auth_types";
        private static final String WANTED_REMEMBER_ME = "wanted_remember_me";
        private static final String WANTED_REMEMBER_ME_OPTIONAL = "wanted_remember_me_optional";
        private static final String IDENTITY_PROFILE_REQUIREMENTS = "identity_profile_requirements";
        private static final String ADVANCED_IDENTITY_PROFILE_REQUIREMENTS = "advanced_identity_profile_requirements";

    }

}
