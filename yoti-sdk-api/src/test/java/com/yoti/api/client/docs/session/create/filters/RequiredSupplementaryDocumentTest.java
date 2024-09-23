package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

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
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()-> RequiredSupplementaryDocument.builder().withDocumentTypes(null));

        assertThat(ex.getMessage(), containsString("documentTypes"));
    }

    @Test
    public void shouldThrowExceptionWhenCountryCodesIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()-> RequiredSupplementaryDocument.builder().withCountryCodes(null));

        assertThat(ex.getMessage(), containsString("countryCodes"));
    }

    @Test
    public void shouldRequireObjectiveBeforeBuilding() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()-> RequiredSupplementaryDocument.builder().build());

        assertThat(ex.getMessage(), containsString("objective"));
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