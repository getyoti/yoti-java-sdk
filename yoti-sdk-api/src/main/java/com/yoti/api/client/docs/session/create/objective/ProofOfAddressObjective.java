package com.yoti.api.client.docs.session.create.objective;

import com.yoti.api.client.docs.DocScanConstants;

public class ProofOfAddressObjective extends Objective {

    ProofOfAddressObjective() {
        super(DocScanConstants.PROOF_OF_ADDRESS);
    }

    public static ProofOfAddressObjective.Builder builder() {
        return new ProofOfAddressObjective.Builder();
    }

    public static class Builder {

        public ProofOfAddressObjective build() {
        return new ProofOfAddressObjective();
    }

    }

}
