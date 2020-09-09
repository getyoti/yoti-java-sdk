package com.yoti.api.client.docs.session.create.objective;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleProofOfAddressObjective extends SimpleObjective implements ProofOfAddressObjective {

    SimpleProofOfAddressObjective() {
        super(DocScanConstants.PROOF_OF_ADDRESS);
    }
}
