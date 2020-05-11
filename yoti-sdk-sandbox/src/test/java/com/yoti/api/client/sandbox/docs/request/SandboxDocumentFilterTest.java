package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import org.junit.Test;

public class SandboxDocumentFilterTest {

    private static final String SOME_COUNTRY_CODE = "GBR";
    private static final String SOME_DOCUMENT_TYPE = "DRIVING_LICENCE";

    @Test
    public void builder_shouldBuildWithDocumentTypeOnly() {
        SandboxDocumentFilter result = SandboxDocumentFilter.builder()
                .withDocumentType(SOME_DOCUMENT_TYPE)
                .build();

        assertThat(result.getCountryCodes(), hasSize(0));
        assertThat(result.getDocumentTypes(), hasSize(1));
        assertThat(result.getDocumentTypes().get(0), is(SOME_DOCUMENT_TYPE));
    }

    @Test
    public void builder_shouldBuildWithCountryCodeOnly() {
        SandboxDocumentFilter result = SandboxDocumentFilter.builder()
                .withCountryCode(SOME_COUNTRY_CODE)
                .build();

        assertThat(result.getDocumentTypes(), hasSize(0));
        assertThat(result.getCountryCodes(), hasSize(1));
        assertThat(result.getCountryCodes().get(0), is(SOME_COUNTRY_CODE));
    }

    @Test
    public void builder_shouldBuildWithBothDocumentTypeAndCountryCode() {
        SandboxDocumentFilter result = SandboxDocumentFilter.builder()
                .withCountryCode(SOME_COUNTRY_CODE)
                .withDocumentType(SOME_DOCUMENT_TYPE)
                .build();

        assertThat(result.getDocumentTypes(), hasSize(1));
        assertThat(result.getDocumentTypes().get(0), is(SOME_DOCUMENT_TYPE));

        assertThat(result.getCountryCodes(), hasSize(1));
        assertThat(result.getCountryCodes().get(0), is(SOME_COUNTRY_CODE));
    }

    @Test
    public void builder_shouldAllowListsForDocumentTypesAndCountryCodes() {
        List<String> countryCodes = Arrays.asList("GBR", "USA", "UKR");
        List<String> documentTypes = Arrays.asList("PASSPORT", "DRIVING_LICENCE");

        SandboxDocumentFilter result = SandboxDocumentFilter.builder()
                .withCountryCodes(countryCodes)
                .withDocumentTypes(documentTypes)
                .build();

        assertThat(result.getCountryCodes(), hasSize(3));
        assertThat(result.getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));

        assertThat(result.getDocumentTypes(), hasSize(2));
        assertThat(result.getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
    }

}
