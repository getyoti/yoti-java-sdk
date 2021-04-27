package com.yoti.api.client.docs.session.create.check.advanced;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RequestedTypeListSourcesTest {

    private static final List<String> SOME_TYPES = Arrays.asList("someType", "someOtherType");

    @Test
    public void builder_shouldBuildWithCorrectListOfTypes() {
        RequestedTypeListSources result = RequestedTypeListSources.builder()
                .withTypes(SOME_TYPES)
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getTypes(), is(SOME_TYPES));
    }
}