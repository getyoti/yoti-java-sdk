package com.yoti.api.client.docs.session.instructions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.yoti.api.client.docs.session.instructions.branch.Branch;
import com.yoti.api.client.docs.session.instructions.document.DocumentProposal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InstructionsTest {

    @Mock ContactProfile contactProfileMock;
    @Mock DocumentProposal documentProposalMock1;
    @Mock DocumentProposal documentProposalMock2;
    @Mock Branch branchMock;

    @Test
    public void builder_shouldBuildWithContactProfile() {
        Instructions result = Instructions.builder()
                .withContactProfile(contactProfileMock)
                .build();

        assertThat(result.getContactProfile(), is(contactProfileMock));
        assertThat(result.getDocuments(), is(nullValue()));
        assertThat(result.getBranch(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithOneDocumentProposal() {
        Instructions result = Instructions.builder()
                .withDocumentProposal(documentProposalMock1)
                .build();

        assertThat(result.getDocuments(), hasSize(1));
        assertThat(result.getDocuments(), contains(documentProposalMock1));
        assertThat(result.getContactProfile(), is(nullValue()));
        assertThat(result.getBranch(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithMultipleDocumentProposals() {
        Instructions result = Instructions.builder()
                .withDocumentProposal(documentProposalMock1)
                .withDocumentProposal(documentProposalMock2)
                .build();

        assertThat(result.getDocuments(), hasSize(2));
        assertThat(result.getDocuments(), contains(documentProposalMock1, documentProposalMock2));
        assertThat(result.getContactProfile(), is(nullValue()));
        assertThat(result.getBranch(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithBranch() {
        Instructions result = Instructions.builder()
                .withBranch(branchMock)
                .build();

        assertThat(result.getBranch(), is(branchMock));
        assertThat(result.getDocuments(), is(nullValue()));
        assertThat(result.getContactProfile(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAllProperties() {
        Instructions result = Instructions.builder()
                .withDocumentProposal(documentProposalMock1)
                .withDocumentProposal(documentProposalMock2)
                .withContactProfile(contactProfileMock)
                .withBranch(branchMock)
                .build();

        assertThat(result.getDocuments(), hasSize(2));
        assertThat(result.getDocuments(), contains(documentProposalMock1, documentProposalMock2));
        assertThat(result.getContactProfile(), is(contactProfileMock));
        assertThat(result.getBranch(), is(branchMock));
    }

}
