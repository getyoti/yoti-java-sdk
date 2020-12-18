package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentRestrictionsFilterTest {

    private static final String EXPECTED_TYPE = "DOCUMENT_RESTRICTIONS";
    private static final String EXPECTED_INCLUSION_WHITELIST = "WHITELIST";
    private static final String EXPECTED_INCLUSION_BLACKLIST = "BLACKLIST";

    private static final List<String> COUNTRY_CODES = Arrays.asList("GBR", "USA", "UKR");
    private static final List<String> DOCUMENT_TYPES = Arrays.asList("PASSPORT", "DRIVING_LICENCE");

    @Mock DocumentRestriction documentRestrictionMock;

    @Test
    public void shouldThrowExceptionForMissingInclusion() {
        try {
            DocumentRestrictionsFilter.builder()
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("inclusion"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldSetCorrectType() {
        DocumentRestrictionsFilter result = DocumentRestrictionsFilter.builder()
                .forWhitelist()
                .withDocumentRestriction(COUNTRY_CODES, DOCUMENT_TYPES)
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldSetCorrectValueForWhitelist() {
        DocumentRestrictionsFilter result = DocumentRestrictionsFilter.builder()
                .forWhitelist()
                .withDocumentRestriction(COUNTRY_CODES, DOCUMENT_TYPES)
                .build();

        assertThat(result.getInclusion(), is(EXPECTED_INCLUSION_WHITELIST));
    }

    @Test
    public void shouldSetCorrectValueForBlacklist() {
        DocumentRestrictionsFilter result = DocumentRestrictionsFilter.builder()
                .forBlacklist()
                .withDocumentRestriction(COUNTRY_CODES, DOCUMENT_TYPES)
                .build();

        assertThat(result.getInclusion(), is(EXPECTED_INCLUSION_BLACKLIST));
    }

    @Test
    public void shouldAddDocumentRestrictionToList() {
        DocumentRestrictionsFilter result = DocumentRestrictionsFilter.builder()
                .forWhitelist()
                .withDocumentRestriction(COUNTRY_CODES, DOCUMENT_TYPES)
                .build();

        assertThat(result.getDocuments(), hasSize(1));
        assertThat(result.getDocuments().get(0).getCountryCodes(), containsInAnyOrder("GBR", "USA", "UKR"));
        assertThat(result.getDocuments().get(0).getDocumentTypes(), containsInAnyOrder("PASSPORT", "DRIVING_LICENCE"));
    }

    @Test
    public void shouldAcceptPreBuiltDocumentRestriction() {
        DocumentRestrictionsFilter result = DocumentRestrictionsFilter.builder()
                .forWhitelist()
                .withDocumentRestriction(documentRestrictionMock)
                .build();

        assertThat(result.getDocuments(), hasSize(1));
        assertThat(result.getDocuments(), containsInAnyOrder(documentRestrictionMock));
    }

}
