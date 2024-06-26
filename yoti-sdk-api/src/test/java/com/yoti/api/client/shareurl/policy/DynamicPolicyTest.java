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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.common.IdentityProfile;
import com.yoti.api.client.common.IdentityProfileScheme;
import com.yoti.api.client.common.Share;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.*;

public class DynamicPolicyTest {

    private static final int EXPECTED_SELFIE_AUTH_TYPE = 1;
    private static final int EXPECTED_PIN_AUTH_TYPE = 2;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void ensuresAnAttributeCanOnlyExistOnce() {
        WantedAttribute wantedAttribute = WantedAttribute.builder()
                .withName("someAttributeName")
                .build();

        DynamicPolicy result = DynamicPolicy.builder()
                .withWantedAttribute(wantedAttribute)
                .withWantedAttribute(wantedAttribute)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(wantedAttribute));
    }

    @Test
    public void buildsWithSimpleAttributes() {
        DynamicPolicy result = DynamicPolicy.builder()
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
        DynamicPolicy result = DynamicPolicy.builder()
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
        DynamicPolicy result = DynamicPolicy.builder()
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
        DynamicPolicy result = DynamicPolicy.builder()
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
        DynamicPolicy result = DynamicPolicy.builder()
                .withAgeUnder(true, 30)
                .withAgeUnder(false, 30)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(DATE_OF_BIRTH, AGE_UNDER + 30, false)));
    }

    @Test
    public void buildsWithAuthTypes() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withSelfieAuthentication(true)
                .withPinAuthentication(true)
                .withWantedAuthType(99)
                .build();

        assertThat(result.getWantedAuthTypes(), hasSize(3));
        assertThat(result.getWantedAuthTypes(), hasItems(EXPECTED_SELFIE_AUTH_TYPE, EXPECTED_PIN_AUTH_TYPE, 99));
    }

    @Test
    public void buildsWithAuthTypesTrue() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withSelfieAuthentication(true)
                .withPinAuthentication(true)
                .withWantedAuthType(99)
                .build();

        assertThat(result.getWantedAuthTypes(), hasSize(3));
        assertThat(result.getWantedAuthTypes(), hasItems(EXPECTED_SELFIE_AUTH_TYPE, EXPECTED_PIN_AUTH_TYPE, 99));
    }

    @Test
    public void buildsWithAuthTypesFalse() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withSelfieAuthentication(false)
                .withPinAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void buildsWithRememberMeFlags() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withWantedRememberMe(true)
                .withWantedRememberMeOptional(true)
                .build();

        assertTrue(result.isWantedRememberMe());
        assertTrue(result.isWantedRememberMeOptional());
    }

    @Test
    public void buildsWithSelfieAuthorisationEnabledThenDisabled() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withSelfieAuthentication(true)
                .withSelfieAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_SELFIE_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void buildsWithSelfieAuthorisationDisabledThenEnabled() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withSelfieAuthentication(false)
                .withSelfieAuthentication(true)
                .build();

        assertThat(result.getWantedAuthTypes(), hasItem(EXPECTED_SELFIE_AUTH_TYPE));
        assertThat(result.getWantedAuthTypes(), hasSize(1));
    }

    @Test
    public void buildsWithPinAuthorisationEnabledThenDisabled() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withPinAuthentication(true)
                .withPinAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_PIN_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void buildsWithPinAuthorisationDisabledThenEnabled() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withPinAuthentication(false)
                .withPinAuthentication(true)
                .build();

        assertThat(result.getWantedAuthTypes(), hasItem(EXPECTED_PIN_AUTH_TYPE));
        assertThat(result.getWantedAuthTypes(), hasSize(1));
    }

    @Test
    public void buildsWithSelfieAuthorisationDisabled() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withSelfieAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_SELFIE_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void buildsWithPinAuthorisationDisabled() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withPinAuthentication(false)
                .build();

        assertThat(result.getWantedAuthTypes(), not(hasItem(EXPECTED_PIN_AUTH_TYPE)));
        assertThat(result.getWantedAuthTypes(), hasSize(0));
    }

    @Test
    public void buildsWithWantedAttributeByNameWithOptionalTrue() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withWantedAttribute(true, GIVEN_NAMES)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, true)));
    }

    @Test
    public void buildsWithWantedAttributeByNameWithOptionalFalse() {
        DynamicPolicy result = DynamicPolicy.builder()
                .withWantedAttribute(false, GIVEN_NAMES)
                .build();

        assertThat(result.getWantedAttributes(), hasSize(1));
        assertThat(result.getWantedAttributes(), hasItem(WantedAttributeMatcher.forAttribute(GIVEN_NAMES, false)));
    }

    @Test
    public void buildWithIdentityProfile() throws IOException {
        IdentityProfileScheme scheme = new IdentityProfileScheme("A_TYPE", "AN_OBJECTIVE");

        IdentityProfile identityProfile = new IdentityProfile("A_FRAMEWORK", scheme);

        JsonNode identityProfileJson = parse(
                DynamicPolicy.builder()
                        .withIdentityProfile(identityProfile)
                        .build()
        ).get(Share.Policy.Profile.IDENTITY_PROFILE_REQUIREMENTS);

        assertThat(identityProfileJson.get(Share.Policy.Profile.TRUST_FRAMEWORK).asText(), is(equalTo(identityProfile.getFramework())));

        JsonNode schemeJsonNode = identityProfileJson.get(Share.Policy.Profile.SCHEME);
        assertThat(schemeJsonNode.get(Share.Policy.Profile.TYPE).asText(), is(equalTo(scheme.getType())));
        assertThat(schemeJsonNode.get(Share.Policy.Profile.OBJECTIVE).asText(), is(equalTo(scheme.getObjective())));
    }

    @Test
    public void buildWithIdentityProfileMap() throws IOException {
        Map<String, Object> scheme = new HashMap<>();
        scheme.put(Share.Policy.Profile.TYPE, "A_TYPE");
        scheme.put(Share.Policy.Profile.OBJECTIVE, "AN_OBJECTIVE");

        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put(Share.Policy.Profile.TRUST_FRAMEWORK, "A_FRAMEWORK");
        identityProfile.put(Share.Policy.Profile.SCHEME, scheme);

        JsonNode identityProfileJson = parse(
                DynamicPolicy.builder()
                        .withIdentityProfile(identityProfile)
                        .build()
        ).get(Share.Policy.Profile.IDENTITY_PROFILE_REQUIREMENTS);

        assertThat(
                identityProfileJson.get(Share.Policy.Profile.TRUST_FRAMEWORK).asText(),
                is(equalTo(identityProfile.get(Share.Policy.Profile.TRUST_FRAMEWORK)))
        );

        JsonNode schemeJsonNode = identityProfileJson.get(Share.Policy.Profile.SCHEME);
        assertThat(schemeJsonNode.get(Share.Policy.Profile.TYPE).asText(), is(equalTo(scheme.get(Share.Policy.Profile.TYPE))));
        assertThat(schemeJsonNode.get(Share.Policy.Profile.OBJECTIVE).asText(), is(equalTo(scheme.get(Share.Policy.Profile.OBJECTIVE))));
    }

    @Test
    public void buildWithAdvancedIdentityProfileMap() throws IOException {
        Map<String, Object> scheme1 = new HashMap<>();
        scheme1.put(Share.Policy.Profile.TYPE, "A_TYPE_1");
        scheme1.put(Share.Policy.Profile.LABEL, "A_LABEL_1");
        scheme1.put(Share.Policy.Profile.OBJECTIVE, "AN_OBJECTIVE_1");

        Map<String, Object> scheme2 = new HashMap<>();
        scheme2.put(Share.Policy.Profile.TYPE, "A_TYPE_2");
        scheme2.put(Share.Policy.Profile.LABEL, "A_LABEL_2");

        Map<String, Object> aProfile = new HashMap<>();
        aProfile.put(Share.Policy.Profile.TRUST_FRAMEWORK, "A_FRAMEWORK_1");
        aProfile.put(Share.Policy.Profile.SCHEMES, toArray(scheme1, scheme2));

        Map<String, Object> documents = new HashMap<>();
        documents.put(Share.Policy.Profile.COUNTRY_CODES, toArray("GBR"));
        documents.put(Share.Policy.Profile.DOCUMENT_TYPES, toArray("PASSPORT", "DRIVING_LICENCE"));

        Map<String, Object> filter = new HashMap<>();
        filter.put(Share.Policy.Profile.TYPE, "DOCUMENT_RESTRICTIONS");
        filter.put(Share.Policy.Profile.INCLUSION, "INCLUDE");
        filter.put(Share.Policy.Profile.DOCUMENTS, toArray(documents));

        Map<String, Object> config = new HashMap<>();
        config.put(Share.Policy.Profile.FILTER, filter);

        Map<String, Object> anotherProfileScheme = new HashMap<>();
        anotherProfileScheme.put(Share.Policy.Profile.TYPE, "A_TYPE_3");
        anotherProfileScheme.put(Share.Policy.Profile.LABEL, "A_LABEL_3");
        anotherProfileScheme.put(Share.Policy.Profile.OBJECTIVE, "AN_OBJECTIVE_3");
        anotherProfileScheme.put(Share.Policy.Profile.CONFIG, config);

        Map<String, Object> anotherProfile = new HashMap<>();
        anotherProfile.put(Share.Policy.Profile.TRUST_FRAMEWORK, "A_FRAMEWORK_2");
        anotherProfile.put(Share.Policy.Profile.SCHEMES, toArray(anotherProfileScheme));

        Map<String, Object> advancedIdentityProfile = new HashMap<>();
        advancedIdentityProfile.put(Share.Policy.Profile.PROFILES, toArray(aProfile, anotherProfile));

        JsonNode advancedIdentityProfileJson = parse(
                DynamicPolicy.builder()
                        .withAdvancedIdentityProfile(advancedIdentityProfile)
                        .build()
        ).get(Share.Policy.Profile.ADVANCED_IDENTITY_PROFILE_REQUIREMENTS);

        assertTrue(advancedIdentityProfileJson.equals(parse(advancedIdentityProfile)));
    }

    private static List<Map<String, Object>> toArray(Map<String, Object>... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    private static List<String> toArray(String... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    private static JsonNode parse(Object obj) throws IOException {
        return MAPPER.readTree(MAPPER.writeValueAsString(obj));
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
