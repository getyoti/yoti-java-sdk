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
public class SimpleRequiredSupplementaryDocumentTest {

    private static final String EXPECTED_TYPE = "SUPPLEMENTARY_DOCUMENT";

    @Mock Objective objectiveMock;

    @Test
    public void shouldHaveTheCorrectType() {
        RequiredSupplementaryDocument result = new SimpleRequiredSupplementaryDocumentBuilder()
                .withObjective(objectiveMock)
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldThrowExceptionWhenDocumentTypesIsNull() {
        try {
            new SimpleRequiredSupplementaryDocumentBuilder()
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
            new SimpleRequiredSupplementaryDocumentBuilder()
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
            new SimpleRequiredSupplementaryDocumentBuilder().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("objective"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void shouldAllowListOfDocumentTypes() {
        RequiredSupplementaryDocument result = new SimpleRequiredSupplementaryDocumentBuilder()
                .withDocumentTypes(Arrays.asList("UTILITY_BILL", "COUNCIL_TAX_BILL"))
                .withObjective(objectiveMock)
                .build();

        assertThat(result.getDocumentTypes(), containsInAnyOrder("UTILITY_BILL", "COUNCIL_TAX_BILL"));
    }

    @Test
    public void shouldAllowListOfCountryCodes() {
        RequiredSupplementaryDocument result = new SimpleRequiredSupplementaryDocumentBuilder()
                .withCountryCodes(Arrays.asList("GBR", "ARE"))
                .withObjective(objectiveMock)
                .build();

        assertThat(result.getCountryCodes(), containsInAnyOrder("GBR", "ARE"));
    }
}