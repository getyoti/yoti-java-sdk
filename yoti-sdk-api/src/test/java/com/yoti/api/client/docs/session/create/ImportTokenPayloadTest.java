package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ImportTokenPayloadTest {

    private static final int SOME_VALUE = 30;

    @Test
    public void builder_withTtl_shouldSetCorrectTtlValue() {
        ImportTokenPayload result = ImportTokenPayload.builder()
                .withTtl(SOME_VALUE)
                .build();

        assertThat(result.getTtl(), is(SOME_VALUE));
    }

    @Test
    public void builder_shouldBuildWithNoValueProvided() {
        ImportTokenPayload result = ImportTokenPayload.builder()
                .build();

        assertThat(result.getTtl(), is(nullValue()));
    }

}
