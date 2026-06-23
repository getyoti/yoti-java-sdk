package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrthogonalRestrictionsFilterTest {

    private static final String EXPECTED_TYPE = "ORTHOGONAL_RESTRICTIONS";
    private static final String EXPECTED_INCLUSION_WHITELIST = "WHITELIST";
    private static final String EXPECTED_INCLUSION_BLACKLIST = "BLACKLIST";

    private static final List<String> COUNTRY_CODES = Arrays.asList("GBR", "USA", "UKR");
    private static final List<String> DOCUMENT_TYPES = Arrays.asList("PASSPORT", "DRIVING_LICENCE");

    @Test
    public void shouldHaveCorrectType() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldBuildWithoutCountryRestrictionOrTypeRestriction() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .build();

        assertThat(result.getCountryRestriction(), is(nullValue()));
        assertThat(result.getTypeRestriction(), is(nullValue()));
    }

    @Test
    public void shouldBuildWhitelistedCountryRestriction() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withWhitelistedCountries(COUNTRY_CODES)
                .build();

        assertThat(result.getCountryRestriction().getInclusion(), is(EXPECTED_INCLUSION_WHITELIST));
        assertThat(result.getCountryRestriction().getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));
    }

    @Test
    public void shouldBuildBlacklistedCountryRestriction() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withBlacklistedCountries(COUNTRY_CODES)
                .build();

        assertThat(result.getCountryRestriction().getInclusion(), is(EXPECTED_INCLUSION_BLACKLIST));
        assertThat(result.getCountryRestriction().getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));
    }

    @Test
    public void shouldBuildWhitelistedDocumentTypeRestriction() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withWhitelistedDocumentTypes(DOCUMENT_TYPES)
                .build();

        assertThat(result.getTypeRestriction().getInclusion(), is(EXPECTED_INCLUSION_WHITELIST));
        assertThat(result.getTypeRestriction().getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
    }

    @Test
    public void shouldBuildBlacklistedDocumentTypeRestriction() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withBlacklistedDocumentTypes(DOCUMENT_TYPES)
                .build();

        assertThat(result.getTypeRestriction().getInclusion(), is(EXPECTED_INCLUSION_BLACKLIST));
        assertThat(result.getTypeRestriction().getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
    }

    @Test
    public void shouldSetAllowNonLatinDocumentsFlag() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withAllowNonLatinDocuments(true)
                .build();

        assertThat(result.getAllowNonLatinDocuments(), is(true));
    }

    @Test
    public void shouldSetNullForAllowNonLatinDocumentsFlagWhenNotProvidedExplicitly() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .build();

        assertThat(result.getAllowNonLatinDocuments(), nullValue());
    }

    @Test
    public void shouldSetAllowExpiredDocumentsFlag() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withAllowExpiredDocuments(true)
                .build();

        assertThat(result.getAllowExpiredDocuments(), is(true));
    }

    @Test
    public void shouldSetNullForAllowExpiredDocumentsFlagWhenNotProvidedExplicitly() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .build();

        assertThat(result.getAllowExpiredDocuments(), nullValue());
    }

    @Test
    public void shouldSetAllowDigitalIdsFlag() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withAllowDigitalIds(true)
                .build();

        assertThat(result.getAllowDigitalIds(), is(true));
    }

    @Test
    public void shouldSetNullForAllowDigitalIdsFlagWhenNotProvidedExplicitly() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .build();

        assertThat(result.getAllowDigitalIds(), nullValue());
    }

    @Test
    public void shouldAddAllowedProvidersByName() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withAllowedProvider("DIGILOCKER")
                .withAllowedProvider("EPHIL_ID_QR")
                .build();

        assertThat(result.getAllowedProviders(), hasSize(2));
        assertThat(result.getAllowedProviders().get(0).getName(), is("DIGILOCKER"));
        assertThat(result.getAllowedProviders().get(1).getName(), is("EPHIL_ID_QR"));
    }

    @Test
    public void shouldSetAllowedProvidersList() {
        List<AllowedProviderPayload> providers = Arrays.asList(
                AllowedProviderPayload.builder().withName("DIGILOCKER").build());

        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .withAllowedProviders(providers)
                .build();

        assertThat(result.getAllowedProviders(), hasSize(1));
        assertThat(result.getAllowedProviders().get(0).getName(), is("DIGILOCKER"));
    }

    @Test
    public void shouldSetNullForAllowedProvidersWhenNotProvidedExplicitly() {
        OrthogonalRestrictionsFilter result = OrthogonalRestrictionsFilter.builder()
                .build();

        assertThat(result.getAllowedProviders(), nullValue());
    }

}
