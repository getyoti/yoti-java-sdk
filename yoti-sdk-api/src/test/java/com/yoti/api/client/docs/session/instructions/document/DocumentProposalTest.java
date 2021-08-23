package com.yoti.api.client.docs.session.instructions.document;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentProposalTest {

    private static final String SOME_REQUIREMENT_ID = "someRequirementId";

    @Mock SelectedDocument selectedDocumentMock;

    @Test
    public void builder_shouldBuildWithRequirementId() {
        DocumentProposal result = DocumentProposal.builder()
                .withRequirementId(SOME_REQUIREMENT_ID)
                .build();

        assertThat(result.getRequirementId(), is(SOME_REQUIREMENT_ID));
        assertThat(result.getDocument(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithSelectedDocument() {
        DocumentProposal result = DocumentProposal.builder()
                .withSelectedDocument(selectedDocumentMock)
                .build();

        assertThat(result.getDocument(), is(selectedDocumentMock));
        assertThat(result.getRequirementId(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAllProperties() {
        DocumentProposal result = DocumentProposal.builder()
                .withRequirementId(SOME_REQUIREMENT_ID)
                .withSelectedDocument(selectedDocumentMock)
                .build();

        assertThat(result.getRequirementId(), is(SOME_REQUIREMENT_ID));
        assertThat(result.getDocument(), is(selectedDocumentMock));
    }

}
