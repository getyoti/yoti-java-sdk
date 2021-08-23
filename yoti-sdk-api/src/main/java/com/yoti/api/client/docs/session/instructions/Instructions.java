package com.yoti.api.client.docs.session.instructions;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.session.instructions.branch.Branch;
import com.yoti.api.client.docs.session.instructions.document.DocumentProposal;

/**
 * Configures the instructions in order to be able to complete In-Branch Verification.
 * <p>
 * In order to provide the end-user with the instructions to complete an In-Branch Verification session,
 * you can supply the information necessary using the {@link Instructions} payload.
 */
public class Instructions {

    private final ContactProfile contactProfile;
    private final List<DocumentProposal> documents;
    private final Branch branch;

    private Instructions(ContactProfile contactProfile, List<DocumentProposal> documents, Branch branch) {
        this.contactProfile = contactProfile;
        this.documents = documents;
        this.branch = branch;
    }

    public static Instructions.Builder builder() {
        return new Instructions.Builder();
    }

    /**
     * The contact profile that will be used for any communication
     * with the end-user
     *
     * @return the contact profile
     */
    public ContactProfile getContactProfile() {
        return contactProfile;
    }

    /**
     * A list of document proposals that will satisfy the sessions requirements.
     *
     * @return the document proposals
     */
    public List<DocumentProposal> getDocuments() {
        return documents;
    }

    /**
     * The branch that has been selected to perform the In-Branch Verification
     *
     * @return the branch
     */
    public Branch getBranch() {
        return branch;
    }

    public static class Builder {

        private ContactProfile contactProfile;
        private List<DocumentProposal> documents;
        private Branch branch;

        private Builder() {}

        /**
         * Sets the contact profile that will be used for any communication
         * with the end-user
         *
         * @param contactProfile the contact profile
         * @return the builder
         */
        public Builder withContactProfile(ContactProfile contactProfile) {
            this.contactProfile = contactProfile;
            return this;
        }

        /**
         * Adds a singular {@link DocumentProposal} to a list of documents that the
         * end-user will need to provide when performing In-Branch Verification
         *
         * @param documentProposal the document proposal
         * @return the builder
         */
        public Builder withDocumentProposal(DocumentProposal documentProposal) {
            if (documents == null) {
                this.documents = new ArrayList<>();
            }
            this.documents.add(documentProposal);
            return this;
        }

        /**
         * Sets the branch that the end-user will perform the In-Branch Verification
         *
         * @param branch the branch
         * @return the builder
         */
        public Builder withBranch(Branch branch) {
            this.branch = branch;
            return this;
        }

        public Instructions build() {
            return new Instructions(contactProfile, documents, branch);
        }

    }

}
