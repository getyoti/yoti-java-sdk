package com.yoti.api.client.spi.remote.call.share;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.spi.remote.IssuingAttribute;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class ThirdPartyAttributeIssuingRequestTest {

    private static final String SOME_ISSUANCE_TOKEN = "someIssuanceToken";
    private static final String SOME_DEFINITION_NAME = "some.third.party.definition";
    private static final String SOME_ISSUING_ATTRIBUTE_VALUE = "someAttributeValue";

    private static final AttributeDefinition SOME_ATTRIBUTE_DEFINITION = new AttributeDefinition(SOME_DEFINITION_NAME);
    private static final IssuingAttribute SOME_ISSUING_ATTRIBUTE = new IssuingAttribute(SOME_ATTRIBUTE_DEFINITION, SOME_ISSUING_ATTRIBUTE_VALUE);

    @Test
    public void Builder_shouldBuildThirdPartyAttributeIssuingRequest() {
        ThirdPartyAttributeIssuingRequestBuilder builder = new ThirdPartyAttributeIssuingRequestBuilder();

        ThirdPartyAttributeIssuingRequest result = builder.withIssuanceToken(SOME_ISSUANCE_TOKEN)
                .withIssuingAttribute(SOME_ISSUING_ATTRIBUTE)
                .build();

        assertThat(result.getIssuingAttributes(), hasSize(1));
        assertEquals(SOME_ISSUANCE_TOKEN, result.getIssuanceToken());

        assertThat(result.getIssuingAttributes().get(0), matchesIssuingAttribute(SOME_ISSUING_ATTRIBUTE));
        assertEquals(result.getIssuingAttributes().get(0).getName(), SOME_ISSUING_ATTRIBUTE.getName());
        assertEquals(result.getIssuingAttributes().get(0).getValue(), SOME_ISSUING_ATTRIBUTE.getValue());
    }

    @Test
    public void Builder_shouldAcceptDefinitionAndValue() {
        ThirdPartyAttributeIssuingRequestBuilder builder = new ThirdPartyAttributeIssuingRequestBuilder();

        ThirdPartyAttributeIssuingRequest result = builder.withIssuanceToken(SOME_ISSUANCE_TOKEN)
                .withIssuingAttribute(SOME_ATTRIBUTE_DEFINITION, SOME_ISSUING_ATTRIBUTE_VALUE)
                .build();

        assertThat(result.getIssuingAttributes(), hasSize(1));
        assertEquals(SOME_ISSUANCE_TOKEN, result.getIssuanceToken());

        assertEquals(result.getIssuingAttributes().get(0).getName(), SOME_ISSUING_ATTRIBUTE.getName());
        assertEquals(result.getIssuingAttributes().get(0).getValue(), SOME_ISSUING_ATTRIBUTE.getValue());
    }

    @Test
    public void Builder_shouldAcceptListOfIssuingAttributes() {
        ThirdPartyAttributeIssuingRequestBuilder builder = new ThirdPartyAttributeIssuingRequestBuilder();

        List<IssuingAttribute> issuingAttributes = Arrays.asList(
                createIssuingAttribute("some.attribute", "someValue"),
                createIssuingAttribute("some.other.attribute", "someOtherValue")
        );

        ThirdPartyAttributeIssuingRequest result = builder.withIssuanceToken(SOME_ISSUANCE_TOKEN)
                .withIssuingAttributes(issuingAttributes)
                .build();

        assertThat(result.getIssuingAttributes(), hasSize(2));
        assertThat(result.getIssuingAttributes().get(0), matchesIssuingAttribute(issuingAttributes.get(0)));
        assertThat(result.getIssuingAttributes().get(1), matchesIssuingAttribute(issuingAttributes.get(1)));
    }

    private IssuingAttribute createIssuingAttribute(String name, String value) {
        AttributeDefinition attributeDefinition = new AttributeDefinition(name);
        return new IssuingAttribute(attributeDefinition, value);
    }

    private Matcher<IssuingAttribute> matchesIssuingAttribute(IssuingAttribute issuingAttribute) {
        return new IssuingAttributeMatcher(issuingAttribute);
    }

    private static class IssuingAttributeMatcher extends TypeSafeMatcher<IssuingAttribute> {

        private static final String DESCRIPTION_TEMPLATE = "\n[\n\tName:%s\n\tValue:%s\n]";

        private IssuingAttribute itemToMatch;

        public IssuingAttributeMatcher(IssuingAttribute itemToMatch) {
            this.itemToMatch = itemToMatch;
        }

        /**
         * Subclasses should implement this. The item will already have been checked for
         * the specific type and will never be null.
         *
         * @param item
         */
        @Override
        protected boolean matchesSafely(IssuingAttribute item) {
            if (!item.getName().equals(itemToMatch.getName())) {
                return false;
            }
            return item.getValue().equals(itemToMatch.getValue());
        }

        private String describe(String name,
                                String value) {
            return String.format(DESCRIPTION_TEMPLATE, name, value);
        }

        /**
         * Generates a description of the object.  The description may be part of a
         * a description of a larger object of which this is just a component, so it
         * should be worded appropriately.
         *
         * @param description The description to be built or appended to.
         */
        @Override
        public void describeTo(Description description) {
            description.appendText(describe(itemToMatch.getName(), itemToMatch.getValue()));
        }
    }

}
