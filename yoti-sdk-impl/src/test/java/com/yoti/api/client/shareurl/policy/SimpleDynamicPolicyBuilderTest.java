package com.yoti.api.client.shareurl.policy;

import static java.lang.String.format;

import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.AGE_OVER;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.AGE_UNDER;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.EMAIL_ADDRESS;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.FAMILY_NAME;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.FULL_NAME;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.GENDER;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.GIVEN_NAMES;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.NATIONALITY;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.PHONE_NUMBER;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.SELFIE;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

public class SimpleDynamicPolicyBuilderTest {

    @Test
    public void ensuresAnAttributeCanOnlyExistOnce() {
        WantedAttribute wantedAttribute = WantedAttributeBuilder.newInstance()
                .withName("someAttributeName")
                .build();

        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withWantedAttribute(wantedAttribute)
                .withWantedAttribute(wantedAttribute)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(wantedAttribute));
    }

    @Test
    public void buildsWithSimpleAttributes() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withFamilyName()
                .withGivenNames()
                .withFullName()
                .withDateOfBirth()
                .withGender()
                .withPostalAddress()
                .withStructuredPostalAddress()
                .withNationality()
                .withPhoneNumber()
                .withSelfie()
                .withEmail()
                .build();

        assertThat(result.getWantedAttributes(), hasSize(11));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(FAMILY_NAME, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(FULL_NAME, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GENDER, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(POSTAL_ADDRESS, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(STRUCTURED_POSTAL_ADDRESS, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(PHONE_NUMBER, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(SELFIE, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(EMAIL_ADDRESS, false)));
    }

    @Test
    public void buildsWithSimpleAttributesAndOptionalFlag() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withFamilyName(true)
                .withGivenNames(true)
                .withFullName(true)
                .withDateOfBirth(true)
                .withGender(true)
                .withPostalAddress(true)
                .withStructuredPostalAddress(true)
                .withNationality(true)
                .withPhoneNumber(true)
                .withSelfie(true)
                .withEmail(true)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(11));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(FAMILY_NAME, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(FULL_NAME, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GENDER, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(POSTAL_ADDRESS, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(STRUCTURED_POSTAL_ADDRESS, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(PHONE_NUMBER, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(SELFIE, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(EMAIL_ADDRESS, true)));
    }

    @Test
    public void buildsWithMultipleAgeDerivedAttributes() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withDateOfBirth()
                .withAgeOver(18)
                .withAgeUnder(30)
                .withAgeUnder(40)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(4));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_OVER + 18, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, false)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 40, false)));
    }

    @Test
    public void buildsWithMultipleAgeDerivedAttributesAndOptionalFlag() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withDateOfBirth(true)
                .withAgeOver(true, 18)
                .withAgeUnder(true, 30)
                .withAgeUnder(true, 40)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(4));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_OVER + 18, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, true)));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 40, true)));
    }

    @Test
    public void shouldOverwriteIdenticalAgeVerificationToEnsureItOnlyExistsOnce() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withAgeUnder(true, 30)
                .withAgeUnder(false, 30)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, false)));
    }

    @Test
    public void buildsWithAuthTypes() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withSelfieAuthorisation()
                .withPinAuthorisation()
                .withWantedAuthType(99)
                .build();

        assertThat(result.getWantedAuthTypes(), Matchers.hasSize(3));
        assertThat(result.getWantedAuthTypes(), hasItems(1, 2, 99));
    }

    @Test
    public void buildsWithAuthTypesTrue() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withSelfieAuthorisation(true)
                .withPinAuthorisation(true)
                .withWantedAuthType(99)
                .build();

        assertThat(result.getWantedAuthTypes(), Matchers.hasSize(3));
        assertThat(result.getWantedAuthTypes(), hasItems(1, 2, 99));
    }

    @Test
    public void buildsWithAuthTypesFalse() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withSelfieAuthorisation(false)
                .withPinAuthorisation(false)
                .build();

        assertThat(result.getWantedAuthTypes(), Matchers.hasSize(0));
    }

    @Test
    public void buildWithRememberMeFlags() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withWantedRememberMe(true)
                .withWantedRememberMeOptional(true)
                .build();

        assertTrue(result.isWantedRememberMe());
        assertTrue(result.isWantedRememberMeOptional());
    }

    @Test
    public void buildWithSelfieAuthorisationEnabledThenDisabled() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withSelfieAuthorisation(true)
                .withSelfieAuthorisation(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(1)));
    }

    @Test
    public void buildWithSelfieAuthorisationDisabledThenEnabled() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withSelfieAuthorisation(false)
                .withSelfieAuthorisation(true)
                .build();

        assertThat(result.getWantedAuthTypes(), hasItem(1));
    }

    @Test
    public void buildWithPinAuthorisationEnabledThenDisabled() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withPinAuthorisation(true)
                .withPinAuthorisation(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(2)));
    }

    @Test
    public void buildWithPinAuthorisationDisabledThenEnabled() {
        DynamicPolicy result = new SimpleDynamicPolicyBuilder()
                .withPinAuthorisation(false)
                .withPinAuthorisation(true)
                .build();

        assertThat(result.getWantedAuthTypes(), hasItem(2));
    }



    private static class WantedAttributeMatcher extends TypeSafeDiagnosingMatcher<WantedAttribute> {

        private static final String TEMPLATE = "{ name: '%s', derivation: '%s',  optional: '%s' }";

        private final String name;
        private final String derivation;
        private final boolean optional;

        private WantedAttributeMatcher(String name, String derivation, boolean optional) {
            this.name = name;
            this.derivation = derivation;
            this.optional = optional;
        }

        @Override
        protected boolean matchesSafely(WantedAttribute wantedAttribute, Description description) {
            description.appendText(format(TEMPLATE, wantedAttribute.getName(), wantedAttribute.getDerivation(), wantedAttribute.isOptional()));
            return nullSafeEquals(name, wantedAttribute.getName()) &&
                    nullSafeEquals(derivation, wantedAttribute.getDerivation()) &&
                    optional == wantedAttribute.isOptional();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(format(TEMPLATE, name, derivation, optional));
        }

        private static boolean nullSafeEquals(Object objA, Object objB) {
            if (objA == null) {
                return objB == null;
            }
            return objA.equals(objB);
        }

        public static WantedAttributeMatcher forAttribute(String name, String derivation, boolean optional) {
            return new WantedAttributeMatcher(name, derivation, optional);
        }

        public static WantedAttributeMatcher forAttribute(String name, boolean optional) {
            return new WantedAttributeMatcher(name, null, optional);
        }

    }

}