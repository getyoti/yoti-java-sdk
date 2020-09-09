package com.yoti.api.client.docs.session.create.objective;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleProofOfAddressObjectiveTest {

    @Test
    public void build_shouldCorrectlyBuildProofOfAddressObjective() {
        Objective result = new SimpleProofOfAddressObjectiveBuilder().build();

        assertThat(result.getType(), is("PROOF_OF_ADDRESS"));
    }
}