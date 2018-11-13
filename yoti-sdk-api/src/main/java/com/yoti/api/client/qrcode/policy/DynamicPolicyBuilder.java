package com.yoti.api.client.qrcode.policy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;

public class DynamicPolicyBuilder {

    private static final int SELFIE_AUTH_TYPE = 1;
    private static final int PIN_AUTH_TYPE = 2;

    private final Map<String, WantedAttribute> wantedAttributes = new HashMap<>();
    private final Set<Integer> wantedAuthTypes = new HashSet<>();
    private boolean wantedRememberMe;
    private boolean wantedRememberMeOptional;

    public DynamicPolicyBuilder withWantedAttribute(WantedAttribute wantedAttribute) {
        this.wantedAttributes.put(wantedAttribute.getName(), wantedAttribute);
        return this;
    }

    private DynamicPolicyBuilder withWantedAttribute(boolean optional, String name) {
        WantedAttribute wantedAttribute = new WantedAttributeBuilder()
                .withName(name)
                .withOptional(optional)
                .build();
        return withWantedAttribute(wantedAttribute);
    }

    public DynamicPolicyBuilder withFamilyName(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.FAMILY_NAME);
    }

    public DynamicPolicyBuilder withGivenNames(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.GIVEN_NAMES);
    }

    public DynamicPolicyBuilder withFullName(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.FULL_NAME);
    }

    public DynamicPolicyBuilder withDateOfBirth(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.DATE_OF_BIRTH);
    }

    public DynamicPolicyBuilder withAgeOver(boolean optional, int age) {
        return withAgeDerivedAttribute(optional, HumanProfileAttributes.AGE_OVER + age);
    }

    public DynamicPolicyBuilder withAgeUnder(boolean optional, int age) {
        return withAgeDerivedAttribute(optional, HumanProfileAttributes.AGE_UNDER + age);
    }

    private DynamicPolicyBuilder withAgeDerivedAttribute(boolean optional, String derivation) {
        WantedAttribute wantedAttribute = new WantedAttributeBuilder()
                .withName(HumanProfileAttributes.DATE_OF_BIRTH)
                .withDerivation(derivation)
                .withOptional(optional)
                .build();
        return withWantedAttribute(wantedAttribute);
    }

    public DynamicPolicyBuilder withGender(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.GENDER);
    }

    public DynamicPolicyBuilder withPostalAddress(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.POSTAL_ADDRESS);
    }

    public DynamicPolicyBuilder withStructuredPostalAddress(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS);
    }

    public DynamicPolicyBuilder withNationality(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.NATIONALITY);
    }

    public DynamicPolicyBuilder withPhoneNumber(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.PHONE_NUMBER);
    }

    public DynamicPolicyBuilder withSelfie(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.SELFIE);
    }

    public DynamicPolicyBuilder withEmail(boolean optional) {
        return withWantedAttribute(optional, HumanProfileAttributes.EMAIL_ADDRESS);
    }

    public DynamicPolicyBuilder withSelfieAuthorisation() {
        return withWantedAuthType(SELFIE_AUTH_TYPE);
    }

    public DynamicPolicyBuilder withPinAuthorisation() {
        return withWantedAuthType(PIN_AUTH_TYPE);
    }

    public DynamicPolicyBuilder withWantedAuthType(int wantedAuthType) {
        this.wantedAuthTypes.add(wantedAuthType);
        return this;
    }

    public DynamicPolicyBuilder withWantedRememberMe(boolean wantedRememberMe) {
        this.wantedRememberMe = wantedRememberMe;
        return this;
    }

    public DynamicPolicyBuilder withWantedRememberMeOptional(boolean wantedRememberMeOptional) {
        this.wantedRememberMeOptional = wantedRememberMeOptional;
        return this;
    }

    public DynamicPolicy build() {
        ServiceLoader<DynamicPolicyFactory> factoryLoader = ServiceLoader.load(DynamicPolicyFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of DynamicPolicyFactory");
        }
        DynamicPolicyFactory dynamicPolicyFactory = factoryLoader.iterator().next();
        return dynamicPolicyFactory.create(wantedAttributes.values(), wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
