package com.yoti.api.client.docs.session.create.objective;

public class SimpleProofOfAddressObjectiveBuilder implements ProofOfAddressObjectiveBuilder {

    @Override
    public ProofOfAddressObjective build() {
        return new SimpleProofOfAddressObjective();
    }
}
