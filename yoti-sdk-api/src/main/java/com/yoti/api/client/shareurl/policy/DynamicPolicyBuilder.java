package com.yoti.api.client.shareurl.policy;

import com.yoti.api.client.shareurl.constraint.Constraint;

import java.util.List;
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

    public abstract DynamicPolicyBuilder withWantedAttribute(boolean optional, String name, List<Constraint> constraints);

    public abstract DynamicPolicyBuilder withWantedAttribute(boolean optional, String name);

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

    /**
     * Deprecated.  Please use withSelfieAuthorisation(boolean enabled) instead.
     */
    @Deprecated
    public abstract DynamicPolicyBuilder withSelfieAuthorisation();

    public abstract DynamicPolicyBuilder withSelfieAuthorisation(boolean enabled);

    /**
     * Deprecated.  Please use withPinAuthorisation(boolean enabled) instead.
     */
    @Deprecated
    public abstract DynamicPolicyBuilder withPinAuthorisation();

    public abstract DynamicPolicyBuilder withPinAuthorisation(boolean enabled);

    public abstract DynamicPolicyBuilder withWantedAuthType(int wantedAuthType);

    public abstract DynamicPolicyBuilder withWantedAuthType(int wantedAuthType, boolean enabled);

    public abstract DynamicPolicyBuilder withWantedRememberMe(boolean wantedRememberMe);

    public abstract DynamicPolicyBuilder withWantedRememberMeOptional(boolean wantedRememberMeOptional);

    public abstract DynamicPolicy build();

}
