package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DocumentRestrictionTest {

    private static final List<String> COUNTRY_CODES = Arrays.asList("GBR", "USA", "UKR");
    private static final List<String> DOCUMENT_TYPES = Arrays.asList("PASSPORT", "DRIVING_LICENCE");

    @Test
    public void shouldBuildWithBothPropertiesOptional() {
        DocumentRestriction result = DocumentRestriction.builder()
                .build();

        assertThat(result.getCountryCodes(), is(nullValue()));
        assertThat(result.getDocumentTypes(), is(nullValue()));
    }

    @Test
    public void shouldBuildWithCountryCodesOnly() {
        DocumentRestriction result = DocumentRestriction.builder()
                .withCountries(COUNTRY_CODES)
                .build();

        assertThat(result.getCountryCodes(), hasSize(3));
        assertThat(result.getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));
        assertThat(result.getDocumentTypes(), is(nullValue()));
    }

    @Test
    public void shouldBuildWithDocumentTypesOnly() {
        DocumentRestriction result = DocumentRestriction.builder()
                .withDocumentTypes(DOCUMENT_TYPES)
                .build();

        assertThat(result.getDocumentTypes(), hasSize(2));
        assertThat(result.getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
        assertThat(result.getCountryCodes(), is(nullValue()));
    }

}
