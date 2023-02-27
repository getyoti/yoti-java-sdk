package com.yoti.api.client.identity.policy;

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
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.common.IdentityProfile;
import com.yoti.api.client.common.IdentityProfileScheme;
import com.yoti.api.client.identity.constraint.Constraint;
import com.yoti.api.client.identity.constraint.SourceConstraint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.*;

public class PolicyTest {

    private static final int EXPECTED_SELFIE_AUTH_TYPE = 1;
    private static final int EXPECTED_PIN_AUTH_TYPE = 2;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void ensuresOnlyOneAttributeWithTheSameName() {
        WantedAttribute wantedAttribute = WantedAttribute.builder()
                .withName("anAttributeName")
                .build();

        Policy result = Policy.builder()
                .withWantedAttribute(wantedAttribute)
                .withWantedAttribute(wantedAttribute)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(wantedAttribute));
    }

    @Test
    public void builderSimpleAttributeWithers() {
        Policy result = Policy.builder()
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

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(11));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(FAMILY_NAME, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(FULL_NAME, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(GENDER, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(POSTAL_ADDRESS, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(STRUCTURED_POSTAL_ADDRESS, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(PHONE_NUMBER, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(SELFIE, false)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(EMAIL_ADDRESS, false)));
    }

    @Test
    public void builderOptionalAttributeWithers() {
        Policy result = Policy.builder()
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

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(11));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(FAMILY_NAME, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(FULL_NAME, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(GENDER, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(POSTAL_ADDRESS, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(STRUCTURED_POSTAL_ADDRESS, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(PHONE_NUMBER, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(SELFIE, true)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(EMAIL_ADDRESS, true)));
    }

    @Test
    public void ensureMultipleAgeDerivedAttributes() {
        Policy result = Policy.builder()
                .withDateOfBirth()
                .withAgeOver(18)
                .withAgeUnder(30)
                .withAgeUnder(40)
                .build();

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(4));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, false)));
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_OVER + 18, false))
        );
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, false))
        );
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 40, false))
        );
    }

    @Test
    public void ensureMultipleOptionalAgeDerivedAttributes() {
        Policy result = Policy.builder()
                .withDateOfBirth(true)
                .withAgeOver(true, 18)
                .withAgeUnder(true, 30)
                .withAgeUnder(true, 40)
                .build();

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(4));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, true)));
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_OVER + 18, true))
        );
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, true))
        );
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 40, true))
        );
    }

    @Test
    public void shouldOverwriteIdenticalAgeVerificationToEnsureItOnlyExistsOnce() {
        Policy result = Policy.builder()
                .withAgeUnder(true, 30)
                .withAgeUnder(false, 30)
                .build();

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(1));
        assertThat(
                wanted,
                hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, false))
        );
    }

    @Test
    public void ensureUniqueConstraintsAttribute() {
        SourceConstraint constraint = SourceConstraint.builder()
                .withSoftPreference(true)
                .withWantedAnchor(
                        WantedAnchor.builder()
                                .withValue("aValue")
                                .withSubType("aSubType")
                                .build()
                ).build();

        List<Constraint> constraints = Collections.singletonList(constraint);
        Policy result = Policy.builder()
                .withWantedAttribute(false, NATIONALITY, constraints)
                .withWantedAttribute(false, NATIONALITY, constraints)
                .build();

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(1));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, false, constraints)));
    }

    @Test
    public void ensureMultipleConstraintsAttribute() {
        WantedAnchor wantedAnchor = WantedAnchor.builder()
                .withValue("aValue")
                .withSubType("aSubType")
                .build();

        List<Constraint> softPresenceConstraints = Collections.singletonList(
                SourceConstraint.builder()
                        .withSoftPreference(true)
                        .withWantedAnchor(wantedAnchor)
                        .build()
        );

        List<Constraint> nonSoftPresentConstraints = Collections.singletonList(
                SourceConstraint.builder()
                        .withWantedAnchor(wantedAnchor)
                        .build()
        );

        Policy result = Policy.builder()
                .withWantedAttribute(true, NATIONALITY, softPresenceConstraints)
                .withWantedAttribute(true, NATIONALITY, nonSoftPresentConstraints)
                .withWantedAttribute(true, NATIONALITY, softPresenceConstraints)
                .withWantedAttribute(true, FULL_NAME, softPresenceConstraints)
                .build();

        Collection<WantedAttribute> wanted = result.getWantedAttributes();
        assertThat(wanted, hasSize(3));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, true, softPresenceConstraints)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(NATIONALITY, true, nonSoftPresentConstraints)));
        assertThat(wanted, hasItem(WantedAttributeMatcher.forAttribute(FULL_NAME, true, softPresenceConstraints)));
    }

    @Test
    public void builderWithAuthTypes() {
        Policy result = Policy.builder()
                .withSelfieAuthentication(true)
                .withPinAuthentication(true)
                .withWantedAuthType(99)
                .build();

        assertThat(result.getWantedAuthTypes(), hasSize(3));
        assertThat(result.getWantedAuthTypes(), hasItems(EXPECTED_SELFIE_AUTH_TYPE, EXPECTED_PIN_AUTH_TYPE, 99));
    }

    @Test
    public void builderWithDisabledAuthTypes() {
        Policy result = Policy.builder()
                .withSelfieAuthentication(false)
                .withPinAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void builderWithRememberMeFlags() {
        Policy result = Policy.builder()
                .withWantedRememberMe(true)
                .withWantedRememberMeOptional(true)
                .build();

        assertTrue(result.isWantedRememberMe());
        assertTrue(result.isWantedRememberMeOptional());
    }

    @Test
    public void builderWithSelfieAuthorisationEnabledThenDisabled() {
        Policy result = Policy.builder()
                .withSelfieAuthentication(true)
                .withSelfieAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_SELFIE_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void builderWithSelfieAuthorisationDisabledThenEnabled() {
        Policy result = Policy.builder()
                .withSelfieAuthentication(false)
                .withSelfieAuthentication(true)
                .build();

        assertThat(result.getWantedAuthTypes(), hasItem(EXPECTED_SELFIE_AUTH_TYPE));
        assertThat(result.getWantedAuthTypes(), hasSize(1));
    }

    @Test
    public void builderWithPinAuthorisationEnabledThenDisabled() {
        Policy result = Policy.builder()
                .withPinAuthentication(true)
                .withPinAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_PIN_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void builderWithPinAuthorisationDisabledThenEnabled() {
        Policy result = Policy.builder()
                .withPinAuthentication(false)
                .withPinAuthentication(true)
                .build();

        assertThat(result.getWantedAuthTypes(), hasItem(EXPECTED_PIN_AUTH_TYPE));
        assertThat(result.getWantedAuthTypes(), hasSize(1));
    }

    @Test
    public void builderWithSelfieAuthorisationDisabled() {
        Policy result = Policy.builder()
                .withSelfieAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_SELFIE_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void builderWithPinAuthorisationDisabled() {
        Policy result = Policy.builder()
                .withPinAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_PIN_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void builderWithWantedAttributeByNameWithOptionalTrue() {
        Policy result = Policy.builder()
                .withWantedAttribute(true, GIVEN_NAMES)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, true)));
    }

    @Test
    public void builderWithWantedAttributeByNameWithOptionalFalse() {
        Policy result = Policy.builder()
                .withWantedAttribute(false, GIVEN_NAMES)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, false)));
    }

    @Test
    public void buildWithIdentityProfile() throws IOException {
        IdentityProfileScheme scheme = new IdentityProfileScheme("A_TYPE", "AN_OBJECTIVE");

        IdentityProfile identityProfile = new IdentityProfile("A_FRAMEWORK", scheme);

        JsonNode json = toJson(identityProfile);

        assertThat(json.get(Property.TRUST_FRAMEWORK).asText(), is(equalTo(identityProfile.getFramework())));

        JsonNode schemeJsonNode = json.get(Property.SCHEME);
        assertThat(schemeJsonNode.get(Property.TYPE).asText(), is(equalTo(scheme.getType())));
        assertThat(schemeJsonNode.get(Property.OBJECTIVE).asText(), is(equalTo(scheme.getObjective())));
    }

    @Test
    public void buildWithIdentityProfileMap() throws IOException {
        Map<String, Object> scheme = new HashMap<>();
        scheme.put(Property.TYPE, "A_TYPE");
        scheme.put(Property.OBJECTIVE, "AN_OBJECTIVE");

        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put(Property.TRUST_FRAMEWORK, "A_FRAMEWORK");
        identityProfile.put(Property.SCHEME, scheme);

        JsonNode json = toJson(identityProfile);

        assertThat(
                json.get(Property.TRUST_FRAMEWORK).asText(),
                is(equalTo(identityProfile.get(Property.TRUST_FRAMEWORK)))
        );

        JsonNode schemeJsonNode = json.get(Property.SCHEME);
        assertThat(schemeJsonNode.get(Property.TYPE).asText(), is(equalTo(scheme.get(Property.TYPE))));
        assertThat(schemeJsonNode.get(Property.OBJECTIVE).asText(), is(equalTo(scheme.get(
                Property.OBJECTIVE))));
    }

    private static JsonNode toJson(Object obj) throws IOException {
        Policy policy = Policy.builder()
                .withIdentityProfile(obj)
                .build();

        return MAPPER.readTree(MAPPER.writeValueAsString(policy.getIdentityProfile()).getBytes(DEFAULT_CHARSET));
    }

    private static final class Property {

        private Property() { }

        private static final String TYPE = "type";
        private static final String SCHEME = "scheme";
        private static final String OBJECTIVE = "objective";
        private static final String TRUST_FRAMEWORK = "trust_framework";

    }

    private static class WantedAttributeMatcher extends TypeSafeDiagnosingMatcher<WantedAttribute> {

        private static final String TEMPLATE = "{ name: '%s', derivation: '%s',  optional: '%s', constraints: %s }";

        private final String name;
        private final String derivation;
        private final boolean optional;
        private final List<Constraint> constraints;

        private WantedAttributeMatcher(String name, String derivation, boolean optional) {
            this.name = name;
            this.derivation = derivation;
            this.optional = optional;
            constraints = new ArrayList<>();
        }

        private WantedAttributeMatcher(String name, boolean optional, List<Constraint> constraints) {
            this.name = name;
            derivation = null;
            this.optional = optional;
            this.constraints = constraints;
        }

        @Override
        protected boolean matchesSafely(WantedAttribute attribute, Description description) {
            description.appendText(format(TEMPLATE,
                    attribute.getName(),
                    attribute.getDerivation(),
                    attribute.isOptional(),
                    attribute.getConstraints()
                )
            );
            return optional == attribute.isOptional()
                    && nullSafeEquals(name, attribute.getName())
                    && nullSafeEquals(derivation, attribute.getDerivation())
                    && nullSafeEquals(constraints, attribute.getConstraints());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(format(TEMPLATE, name, derivation, optional, constraints));
        }

        private static boolean nullSafeEquals(Object o, Object other) {
            if (o == null) {
                return other == null;
            }
            return o.equals(other);
        }

        private static WantedAttributeMatcher forAttribute(String name, boolean optional) {
            return new WantedAttributeMatcher(name, null, optional);
        }

        private static WantedAttributeMatcher forAttribute(String name, String derivation, boolean optional) {
            return new WantedAttributeMatcher(name, derivation, optional);
        }

        private static WantedAttributeMatcher forAttribute(
                String name,
                boolean optional,
                List<Constraint> constraints) {
            return new WantedAttributeMatcher(name, optional, constraints);
        }

    }

}
