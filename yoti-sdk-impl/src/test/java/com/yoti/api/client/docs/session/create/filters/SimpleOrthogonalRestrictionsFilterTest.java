package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleOrthogonalRestrictionsFilterTest {

    private static final String EXPECTED_TYPE = "ORTHOGONAL_RESTRICTIONS";
    private static final String EXPECTED_INCLUSION_WHITELIST = "WHITELIST";
    private static final String EXPECTED_INCLUSION_BLACKLIST = "BLACKLIST";

    private static final List<String> COUNTRY_CODES = Arrays.asList("GBR", "USA", "UKR");
    private static final List<String> DOCUMENT_TYPES = Arrays.asList("PASSPORT", "DRIVING_LICENCE");

    @Test
    public void shouldHaveCorrectType() {
        OrthogonalRestrictionsFilter result = new SimpleOrthogonalRestrictionsFilterBuilder()
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldBuildWithoutCountryRestrictionOrTypeRestriction() {
        OrthogonalRestrictionsFilter result = new SimpleOrthogonalRestrictionsFilterBuilder()
                .build();

        assertThat(result.getCountryRestriction(), is(nullValue()));
        assertThat(result.getTypeRestriction(), is(nullValue()));
    }

    @Test
    public void shouldBuildWhitelistedCountryRestriction() {
        OrthogonalRestrictionsFilter result = new SimpleOrthogonalRestrictionsFilterBuilder()
                .withWhitelistedCountries(COUNTRY_CODES)
                .build();

        assertThat(result.getCountryRestriction().getInclusion(), is(EXPECTED_INCLUSION_WHITELIST));
        assertThat(result.getCountryRestriction().getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));
    }

    @Test
    public void shouldBuildBlacklistedCountryRestriction() {
        OrthogonalRestrictionsFilter result = new SimpleOrthogonalRestrictionsFilterBuilder()
                .withBlacklistedCountries(COUNTRY_CODES)
                .build();

        assertThat(result.getCountryRestriction().getInclusion(), is(EXPECTED_INCLUSION_BLACKLIST));
        assertThat(result.getCountryRestriction().getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));
    }

    @Test
    public void shouldBuildWhitelistedDocumentTypeRestriction() {
        OrthogonalRestrictionsFilter result = new SimpleOrthogonalRestrictionsFilterBuilder()
                .withWhitelistedDocumentTypes(DOCUMENT_TYPES)
                .build();

        assertThat(result.getTypeRestriction().getInclusion(), is(EXPECTED_INCLUSION_WHITELIST));
        assertThat(result.getTypeRestriction().getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
    }

    @Test
    public void shouldBuildBlacklistedDocumentTypeRestriction() {
        OrthogonalRestrictionsFilter result = new SimpleOrthogonalRestrictionsFilterBuilder()
                .withBlacklistedDocumentTypes(DOCUMENT_TYPES)
                .build();

        assertThat(result.getTypeRestriction().getInclusion(), is(EXPECTED_INCLUSION_BLACKLIST));
        assertThat(result.getTypeRestriction().getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
    }

}
