package com.yoti.api.client;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.spi.remote.AttributeParser;
import com.yoti.api.client.spi.remote.JpegAttributeValue;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto;

import com.google.protobuf.ByteString;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class HumanProfileTest {

    private static final String SOME_KEY = "someKey";
    private static final String STARTS_WITH = "startsWith";
    private static final String STRING_VALUE = "test value";
    private static final Integer INTEGER_VALUE = 1;

    HumanProfile testObj;

    @Mock Profile profileMock;

    Attribute<String> ageUnder18Attribute = new Attribute<>("age_under:18", "false");
    Attribute<String> ageUnder21Attribute = new Attribute<>("age_under:21", "true");
    Attribute<String> ageOver18Attribute = new Attribute<>("age_over:18", "true");
    Attribute<String> ageOver21Attribute = new Attribute<>("age_over:21", "false");
    List<Attribute<String>> ageUnderAttributes = asList(ageUnder18Attribute, ageUnder21Attribute);
    List<Attribute<String>> ageOverAttributes = asList(ageOver18Attribute, ageOver21Attribute);

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldFailConstructionForNullAttributes() {
        new HumanProfile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttribute_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(emptyList());

        profile.getAttribute(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByName_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(emptyList());

        profile.getAttribute(null);
    }

    @Test
    public void getAttribute_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttributeByName_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(emptyList());

        assertNull(profile.getAttribute(SOME_KEY));
    }

    @Test
    public void getAttribute_shouldReturnValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<?> result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<?> result = profile.getAttribute(SOME_KEY);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeTyped_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAttributeByNameTyped_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(emptyList());

        profile.getAttribute(null, Object.class);
    }

    @Test
    public void getAttributeTyped_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnNullValueForNonExistingKey() {
        Profile profile = new HumanProfile(emptyList());

        assertNull(profile.getAttribute(SOME_KEY, Object.class));
    }

    @Test
    public void getAttributeTyped_shouldReturnTypedValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByNameTyped_shouldReturnTypedValueForExistingKey() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, STRING_VALUE));

        Attribute<String> result = profile.getAttribute(SOME_KEY, String.class);

        assertEquals(STRING_VALUE, result.getValue());
    }

    @Test
    public void getAttributeTyped_shouldThrowExceptionForForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        try {
            profile.getAttribute(SOME_KEY, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getAttributeByNameTyped_shouldThrowExceptionForForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        try {
            profile.getAttribute(SOME_KEY, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }
        fail("Expected an exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributesStartingWith_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributesStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributesStartingWith_shouldReturnEmptyListWhenNoMatches() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAttributesStartingWith_shouldThrowExceptionForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH, INTEGER_VALUE));

        try {
            profile.findAttributesStartingWith(STARTS_WITH, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }

        fail("Expected an exception");
    }

    @Test
    public void findAttributesStartingWith_shouldReturnTypedValuesForMatchingKey() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        List<Attribute<Integer>> result = profile.findAttributesStartingWith(STARTS_WITH, Integer.class);

        assertEquals(1, result.size());
        assertEquals(INTEGER_VALUE, result.get(0).getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAttributeStartingWith_shouldThrowExceptionForNullAttributeName() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        profile.findAttributeStartingWith(null, Integer.class);
    }

    @Test
    public void findAttributeStartingWith_shouldReturnNullWhenNoMatchingName() {
        Profile profile = new HumanProfile(asAttributeList(SOME_KEY, INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertNull(result);
    }

    @Test
    public void findAttributeStartingWith_shouldThrowExceptionForMismatchingType() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH, INTEGER_VALUE));

        try {
            profile.findAttributeStartingWith(STARTS_WITH, String.class);
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().contains("java.lang.String"));
            assertTrue(e.getMessage().contains("java.lang.Integer"));
            return;
        }

        fail("Expected an exception");
    }

    @Test
    public void findAttributeStartingWith_shouldReturnTypedValueForMatchingKey() {
        Profile profile = new HumanProfile(asAttributeList(STARTS_WITH + ":restOfKey", INTEGER_VALUE));

        Attribute<Integer> result = profile.findAttributeStartingWith(STARTS_WITH, Integer.class);

        assertEquals(INTEGER_VALUE, result.getValue());
    }

    @Test
    public void getAttributeByName_shouldReturnFirstMatchingAttributeWhenMultipleWithSameName() {
        List<Attribute<?>> attributeList = new ArrayList<>();
        attributeList.add(createAttribute("some_attribute", "firstValue"));
        attributeList.add(createAttribute("some_attribute", "secondValue"));

        Profile profile = new HumanProfile(attributeList);

        Attribute<String> result = profile.getAttribute("some_attribute", String.class);

        assertEquals("firstValue", result.getValue());
    }

    @Test
    public void getAgeVerifications_shouldBeNullSafe() {
        testObj = new HumanProfile(emptyList());

        List<AgeVerification> result = testObj.getAgeVerifications();

        assertThat(result, hasSize(0));
    }

    @Test
    public void getAgeVerifications_shouldOnlySearchAgeVerificationsOnce() {
        List<Attribute<?>> attributes = new ArrayList<>();
        attributes.addAll(ageUnderAttributes);
        attributes.addAll(ageOverAttributes);

        testObj = new HumanProfile(attributes);

        testObj.getAgeVerifications();
        List<AgeVerification> result = testObj.getAgeVerifications();

        assertThat(result, hasSize(4));
        assertThat(new HashSet<>(result), hasSize(4));
        assertThat(result.get(0).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        assertThat(result.get(1).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        assertThat(result.get(2).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
        assertThat(result.get(3).getAttribute(), isOneOf(ageUnder18Attribute, ageUnder21Attribute, ageOver18Attribute, ageOver21Attribute));
    }

    @Test
    public void findAgeOverVerification_returnsNullForNoMatch() {
        testObj = new HumanProfile(emptyList());

        testObj.findAgeOverVerification(18);
        AgeVerification result = testObj.findAgeOverVerification(18);

        assertNull(result);
    }

    @Test
    public void findAgeOverVerification_shouldOnlySearchWrappedProfileOnce() {
        List<Attribute<?>> attributes = new ArrayList<>();
        attributes.addAll(ageUnderAttributes);
        attributes.addAll(ageOverAttributes);

        testObj = new HumanProfile(attributes);

        testObj.findAgeOverVerification(18);
        AgeVerification result = testObj.findAgeOverVerification(18);

        assertSame(ageOver18Attribute, result.getAttribute());
    }

    @Test
    public void findAgeUnderVerification_returnsNullForNoMatch() {
        testObj = new HumanProfile(emptyList());

        testObj.findAgeOverVerification(18);
        AgeVerification result = testObj.findAgeUnderVerification(18);

        assertNull(result);
    }

    @Test
    public void findAgeUnderVerification_shouldOnlySearchAgeVerificationsOnce() {
        List<Attribute<?>> attributes = new ArrayList<>();
        attributes.addAll(ageUnderAttributes);
        attributes.addAll(ageOverAttributes);

        testObj = new HumanProfile(attributes);

        testObj.findAgeUnderVerification(18);
        AgeVerification result = testObj.findAgeUnderVerification(18);

        assertSame(ageUnder18Attribute, result.getAttribute());
    }

    @Test
    public void getIdentityProfileReport_shouldReturnValueForIdentityProfileReport() throws ParseException, IOException {
        String identityReportJson = "{\"identity_assertion\":\"\",\"verification_report\":{"
                + "\"report_id\":\"anId\",\"timestamp\":\"2022-07-20T02:00:00Z\",\"subject_id\":\"aSubject\","
                + "\"trust_framework\":\"aFramework\"}}";

        String attributeName = "identity_profile_report";

        AttrProto.Attribute identity_profile_report = AttrProto.Attribute.newBuilder()
                .setName(attributeName)
                .setContentType(ContentTypeProto.ContentType.JSON)
                .setValue(ByteString.copyFromUtf8(identityReportJson))
                .build();

        List<Attribute<?>> attributes = new ArrayList<>();

        attributes.add(AttributeParser.fromProto(identity_profile_report));

        HumanProfile humanProfile = new HumanProfile(attributes);

        Attribute<Map<String, Object>> identityProfileReport = humanProfile.getIdentityProfileReport();

        assertThat(identityProfileReport, is(notNullValue()));
        assertThat(identityProfileReport.getName(), is(equalTo(attributeName)));

        Map<?, ?> value = identityProfileReport.getValue();
        assertThat(value, is(notNullValue()));
        assertThat(value.get("identity_assertion"), is(notNullValue()));
        assertThat(value.get("verification_report"), is(notNullValue()));
    }

    @Test
    public void getAttributeById_shouldReturnAttributeById() {
        String anId = "anId";
        Attribute<JpegAttributeValue> imageAttribute = createImageAttribute(anId);

        List<Attribute<?>> attributes = new ArrayList<>();
        attributes.add(imageAttribute);
        attributes.add(createImageAttribute("anotherId"));

        HumanProfile humanProfile = new HumanProfile(attributes);

        Attribute<?> attribute = humanProfile.getAttributeById(anId);

        assertThat(attribute, is(imageAttribute));
    }

    @Test
    public void getAttributeById_shouldReturnNull() {
        List<Attribute<?>> attributes = new ArrayList<>();
        attributes.add(createImageAttribute("anId"));
        attributes.add(createImageAttribute("anotherId"));

        HumanProfile humanProfile = new HumanProfile(attributes);

        Attribute<?> attribute = humanProfile.getAttributeById("unknown_id");

        assertNull(attribute);
    }

    private static Attribute<JpegAttributeValue> createImageAttribute(String id) {
        return new Attribute<>(
                id,
                String.format("name_%s", id),
                new JpegAttributeValue("image".getBytes(StandardCharsets.UTF_8))
        );
    }

    private static <T> List<Attribute<?>> asAttributeList(String key, T o) {
        Attribute<?> a = createAttribute(key, o);
        return Collections.singletonList(a);
    }

    private static <T> Attribute<?> createAttribute(String key, T o) {
        return new Attribute<>(key, o);
    }

}
