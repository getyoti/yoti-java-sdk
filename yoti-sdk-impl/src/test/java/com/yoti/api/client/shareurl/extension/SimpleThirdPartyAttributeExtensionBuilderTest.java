package com.yoti.api.client.shareurl.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.spi.remote.call.YotiConstants;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SimpleThirdPartyAttributeExtensionBuilderTest {

    private static final Date SOME_DATE = new Date();
    private static final String SOME_DEFINITION = "com.thirdparty.id";

    @Test
    public void shouldFailForNullExpiryDate() {
        try {
            new SimpleThirdPartyAttributeExtensionBuilder()
                    .withExpiryDate(null)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("expiryDate"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldFailForNullDefinition() {
        try {
            new SimpleThirdPartyAttributeExtensionBuilder()
                    .withDefinition(null)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("definition"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldFailForEmptyDefinition() {
        try {
            new SimpleThirdPartyAttributeExtensionBuilder()
                    .withDefinition("")
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("definition"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldBuildThirdPartyAttributeExtensionWithGivenValues() {
        Extension<ThirdPartyAttributeContent> extension = new SimpleThirdPartyAttributeExtensionBuilder()
                .withExpiryDate(SOME_DATE)
                .withDefinition(SOME_DEFINITION)
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat(YotiConstants.RFC3339_PATTERN_MILLIS);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedTestDate = sdf.format(SOME_DATE);

        assertEquals(ExtensionConstants.THIRD_PARTY_ATTRIBUTE, extension.getType());
        assertEquals(formattedTestDate, extension.getContent().getExpiryDate());

        List<AttributeDefinition> definitions = extension.getContent().getDefinitions();
        assertThat(definitions.size(), is(1));
        assertThat(definitions.get(0).getName(), is(SOME_DEFINITION));
    }

    @Test
    public void shouldBuildThirdPartyAttributeExtensionWithCorrectDateValue() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        Date date = new Date();

        Extension<ThirdPartyAttributeContent> extension = new SimpleThirdPartyAttributeExtensionBuilder()
                .withExpiryDate(date)
                .withDefinition(SOME_DEFINITION)
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat(YotiConstants.RFC3339_PATTERN_MILLIS);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedTestDate = sdf.format(date);

        assertEquals(ExtensionConstants.THIRD_PARTY_ATTRIBUTE, extension.getType());
        assertEquals(formattedTestDate, extension.getContent().getExpiryDate());

        List<AttributeDefinition> definitions = extension.getContent().getDefinitions();
        assertThat(definitions.size(), is(1));
        assertThat(definitions.get(0).getName(), is(SOME_DEFINITION));
    }

    @Test
    public void shouldBuildThirdPartyAttributeExtensionWithMultipleDefinitions() {
        List<String> theDefinitions = new ArrayList<>();
        theDefinitions.add("firstDefinition");
        theDefinitions.add("secondDefinition");

        Extension<ThirdPartyAttributeContent> extension = new SimpleThirdPartyAttributeExtensionBuilder()
                .withExpiryDate(SOME_DATE)
                .withDefinitions(theDefinitions)
                .build();

        assertEquals(ExtensionConstants.THIRD_PARTY_ATTRIBUTE, extension.getType());

        List<AttributeDefinition> definitions = extension.getContent().getDefinitions();
        assertThat(definitions.size(), is(2));
        assertThat(definitions.get(0).getName(), is("firstDefinition"));
        assertThat(definitions.get(1).getName(), is("secondDefinition"));
    }

    @Test
    public void shouldOverwriteSingularlyAddedDefinition() {
        List<String> theDefinitions = new ArrayList<>();
        theDefinitions.add("firstDefinition");
        theDefinitions.add("secondDefinition");

        Extension<ThirdPartyAttributeContent> extension = new SimpleThirdPartyAttributeExtensionBuilder()
                .withExpiryDate(SOME_DATE)
                .withDefinition(SOME_DEFINITION)
                .withDefinitions(theDefinitions)
                .build();

        assertEquals(ExtensionConstants.THIRD_PARTY_ATTRIBUTE, extension.getType());

        List<AttributeDefinition> definitions = extension.getContent().getDefinitions();
        assertThat(definitions.size(), is(2));
        assertThat(definitions.get(0).getName(), is("firstDefinition"));
        assertThat(definitions.get(1).getName(), is("secondDefinition"));
    }

}
