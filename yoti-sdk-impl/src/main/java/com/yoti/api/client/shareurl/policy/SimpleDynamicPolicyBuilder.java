package com.yoti.api.client.shareurl.policy;

import java.util.*;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.shareurl.constraint.Constraint;

public class SimpleDynamicPolicyBuilder extends DynamicPolicyBuilder {

    private static final int SELFIE_AUTH_TYPE = 1;
    private static final int PIN_AUTH_TYPE = 2;

    private final Map<String, WantedAttribute> wantedAttributes = new HashMap<>();
    private final Set<Integer> wantedAuthTypes = new HashSet<>();
    private boolean wantedRememberMe;
    private boolean wantedRememberMeOptional;

    @Override
    protected DynamicPolicyBuilder createDynamicPolicyBuilder() {
        return new SimpleDynamicPolicyBuilder();
    }

    @Override
    public DynamicPolicyBuilder withWantedAttribute(WantedAttribute wantedAttribute) {
        String key = wantedAttribute.getDerivation() != null ? wantedAttribute.getDerivation() : wantedAttribute.getName();
        this.wantedAttributes.put(key, wantedAttribute);
        return this;
    }

    @Override
    public DynamicPolicyBuilder withWantedAttribute(boolean optional, String name, List<Constraint> constraints) {
        WantedAttribute wantedAttribute = new SimpleWantedAttributeBuilder()
                .withName(name)
                .withOptional(optional)
                .withConstraints(constraints)
                .build();
        return withWantedAttribute(wantedAttribute);
    }

    @Override
    public DynamicPolicyBuilder withWantedAttribute(boolean optional, String name) {
        return withWantedAttribute(optional, name, Collections.<Constraint>emptyList());
    }

    @Override
    public DynamicPolicyBuilder withFamilyName() {
        return withFamilyName(false);
    }

    @Override
    public DynamicPolicyBuilder withFamilyName(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.FAMILY_NAME);
    }

    @Override
    public DynamicPolicyBuilder withGivenNames() {
        return withGivenNames(false);
    }

    @Override
    public DynamicPolicyBuilder withGivenNames(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.GIVEN_NAMES);
    }

    @Override
    public DynamicPolicyBuilder withFullName() {
        return withFullName(false);
    }

    @Override
    public DynamicPolicyBuilder withFullName(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.FULL_NAME);
    }

    @Override
    public DynamicPolicyBuilder withDateOfBirth() {
        return withDateOfBirth(false);
    }

    @Override
    public DynamicPolicyBuilder withDateOfBirth(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH);
    }

    @Override
    public DynamicPolicyBuilder withAgeOver(int age) {
        return withAgeOver(false, age);
    }

    @Override
    public DynamicPolicyBuilder withAgeOver(boolean optional, int age) {
        return withAgeDerivedAttribute(optional, AttributeConstants.HumanProfileAttributes.AGE_OVER + age);
    }

    @Override
    public DynamicPolicyBuilder withAgeUnder(int age) {
        return withAgeUnder(false, age);
    }

    @Override
    public DynamicPolicyBuilder withAgeUnder(boolean optional, int age) {
        return withAgeDerivedAttribute(optional, AttributeConstants.HumanProfileAttributes.AGE_UNDER + age);
    }

    private DynamicPolicyBuilder withAgeDerivedAttribute(boolean optional, String derivation) {
        WantedAttribute wantedAttribute = new SimpleWantedAttributeBuilder()
                .withName(AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH)
                .withDerivation(derivation)
                .withOptional(optional)
                .build();
        return withWantedAttribute(wantedAttribute);
    }

    @Override
    public DynamicPolicyBuilder withGender() {
        return withGender(false);
    }

    @Override
    public DynamicPolicyBuilder withGender(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.GENDER);
    }

    @Override
    public DynamicPolicyBuilder withPostalAddress() {
        return withPostalAddress(false);
    }

    @Override
    public DynamicPolicyBuilder withPostalAddress(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS);
    }

    @Override
    public DynamicPolicyBuilder withStructuredPostalAddress() {
        return withStructuredPostalAddress(false);
    }

    @Override
    public DynamicPolicyBuilder withStructuredPostalAddress(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS);
    }

    @Override
    public DynamicPolicyBuilder withNationality() {
        return withNationality(false);
    }

    @Override
    public DynamicPolicyBuilder withNationality(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.NATIONALITY);
    }

    @Override
    public DynamicPolicyBuilder withPhoneNumber() {
        return withPhoneNumber(false);
    }

    @Override
    public DynamicPolicyBuilder withPhoneNumber(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.PHONE_NUMBER);
    }

    @Override
    public DynamicPolicyBuilder withSelfie() {
        return withSelfie(false);
    }

    @Override
    public DynamicPolicyBuilder withSelfie(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.SELFIE);
    }

    @Override
    public DynamicPolicyBuilder withEmail() {
        return withEmail(false);
    }

    @Override
    public DynamicPolicyBuilder withEmail(boolean optional) {
        return withWantedAttribute(optional, AttributeConstants.HumanProfileAttributes.EMAIL_ADDRESS);
    }

    @Override
    @Deprecated
    public DynamicPolicyBuilder withSelfieAuthorisation() {
        return withSelfieAuthorisation(true);
    }

    @Override
    public DynamicPolicyBuilder withSelfieAuthorisation(boolean enabled) {
        return this.withWantedAuthType(SELFIE_AUTH_TYPE, enabled);
    }

    @Override
    @Deprecated
    public DynamicPolicyBuilder withPinAuthorisation() {
        return withPinAuthorisation(true);
    }

    @Override
    public DynamicPolicyBuilder withPinAuthorisation(boolean enabled) {
        return this.withWantedAuthType(PIN_AUTH_TYPE, enabled);
    }

    @Override
    public DynamicPolicyBuilder withWantedAuthType(int wantedAuthType) {
        this.wantedAuthTypes.add(wantedAuthType);
        return this;
    }

    @Override
    public DynamicPolicyBuilder withWantedAuthType(int wantedAuthType, boolean enabled) {
        if (enabled) {
            return this.withWantedAuthType(wantedAuthType);
        } else {
            this.wantedAuthTypes.remove(wantedAuthType);
            return this;
        }
    }

    @Override
    public DynamicPolicyBuilder withWantedRememberMe(boolean wantedRememberMe) {
        this.wantedRememberMe = wantedRememberMe;
        return this;
    }

    @Override
    public DynamicPolicyBuilder withWantedRememberMeOptional(boolean wantedRememberMeOptional) {
        this.wantedRememberMeOptional = wantedRememberMeOptional;
        return this;
    }

    @Override
    public DynamicPolicy build() {
        return new SimpleDynamicPolicy(wantedAttributes.values(), wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
