package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.Arrays;

import com.yoti.api.client.docs.session.create.objective.Objective;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequiredSupplementaryDocumentTest {

    private static final String EXPECTED_TYPE = "SUPPLEMENTARY_DOCUMENT";

    @Mock Objective objectiveMock;

    @Test
    public void shouldHaveTheCorrectType() {
        RequiredSupplementaryDocument result = RequiredSupplementaryDocument.builder()
                .withObjective(objectiveMock)
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldThrowExceptionWhenDocumentTypesIsNull() {
        try {
            RequiredSupplementaryDocument.builder()
                .withDocumentTypes(null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("documentTypes"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldThrowExceptionWhenCountryCodesIsNull() {
        try {
            RequiredSupplementaryDocument.builder()
                .withCountryCodes(null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("countryCodes"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldRequireObjectiveBeforeBuilding() {
        try {
            RequiredSupplementaryDocument.builder()
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("objective"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldAllowListOfDocumentTypes() {
        RequiredSupplementaryDocument result = RequiredSupplementaryDocument.builder()
                .withDocumentTypes(Arrays.asList("UTILITY_BILL", "COUNCIL_TAX_BILL"))
                .withObjective(objectiveMock)
                .build();

        assertThat(result.getDocumentTypes(), containsInAnyOrder("UTILITY_BILL", "COUNCIL_TAX_BILL"));
    }

    @Test
    public void shouldAllowListOfCountryCodes() {
        RequiredSupplementaryDocument result = RequiredSupplementaryDocument.builder()
                .withCountryCodes(Arrays.asList("GBR", "ARE"))
                .withObjective(objectiveMock)
                .build();

        assertThat(result.getCountryCodes(), containsInAnyOrder("GBR", "ARE"));
    }
}