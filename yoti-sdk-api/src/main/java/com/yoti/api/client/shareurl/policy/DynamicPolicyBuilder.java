package com.yoti.api.client.shareurl.policy;

import java.util.ServiceLoader;

public abstract class DynamicPolicyBuilder {

    public static final DynamicPolicyBuilder newInstance() {
        ServiceLoader<DynamicPolicyBuilder> dynamicPolicyBuilderLoader = ServiceLoader.load(DynamicPolicyBuilder.class);
        if (!dynamicPolicyBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + DynamicPolicyBuilder.class.getSimpleName());
        }
        DynamicPolicyBuilder dynamicPolicyBuilder = dynamicPolicyBuilderLoader.iterator().next();
        return dynamicPolicyBuilder.createDynamicPolicyBuilder();
    }

    protected abstract DynamicPolicyBuilder createDynamicPolicyBuilder();

    public abstract DynamicPolicyBuilder withWantedAttribute(WantedAttribute wantedAttribute);

    public abstract DynamicPolicyBuilder withFamilyName(boolean optional);

    public abstract DynamicPolicyBuilder withFamilyName();

    public abstract DynamicPolicyBuilder withGivenNames(boolean optional);

    public abstract DynamicPolicyBuilder withGivenNames();

    public abstract DynamicPolicyBuilder withFullName(boolean optional);

    public abstract DynamicPolicyBuilder withFullName();

    public abstract DynamicPolicyBuilder withDateOfBirth(boolean optional);

    public abstract DynamicPolicyBuilder withDateOfBirth();

    public abstract DynamicPolicyBuilder withAgeOver(boolean optional, int age);

    public abstract DynamicPolicyBuilder withAgeOver(int age);

    public abstract DynamicPolicyBuilder withAgeUnder(boolean optional, int age);

    public abstract DynamicPolicyBuilder withAgeUnder(int age);

    public abstract DynamicPolicyBuilder withGender(boolean optional);

    public abstract DynamicPolicyBuilder withGender();

    public abstract DynamicPolicyBuilder withPostalAddress(boolean optional);

    public abstract DynamicPolicyBuilder withPostalAddress();

    public abstract DynamicPolicyBuilder withStructuredPostalAddress(boolean optional);

    public abstract DynamicPolicyBuilder withStructuredPostalAddress();

    public abstract DynamicPolicyBuilder withNationality(boolean optional);

    public abstract DynamicPolicyBuilder withNationality();

    public abstract DynamicPolicyBuilder withPhoneNumber(boolean optional);

    public abstract DynamicPolicyBuilder withPhoneNumber();

    public abstract DynamicPolicyBuilder withSelfie(boolean optional);

    public abstract DynamicPolicyBuilder withSelfie();

    public abstract DynamicPolicyBuilder withEmail(boolean optional);

    public abstract DynamicPolicyBuilder withEmail();

    public abstract DynamicPolicyBuilder withSelfieAuthorisation();

    public abstract DynamicPolicyBuilder withPinAuthorisation();

    public abstract DynamicPolicyBuilder withWantedAuthType(int wantedAuthType);

    public abstract DynamicPolicyBuilder withWantedRememberMe(boolean wantedRememberMe);

    public abstract DynamicPolicyBuilder withWantedRememberMeOptional(boolean wantedRememberMeOptional);

    public abstract DynamicPolicy build();

}
