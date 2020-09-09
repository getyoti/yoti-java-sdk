package com.yoti.api.client.docs.session.create.objective;

public class SimpleObjectiveBuilderFactory extends ObjectiveBuilderFactory {

    @Override
    public ProofOfAddressObjectiveBuilder forProofOfAddress() {
        return new SimpleProofOfAddressObjectiveBuilder();
    }
}
