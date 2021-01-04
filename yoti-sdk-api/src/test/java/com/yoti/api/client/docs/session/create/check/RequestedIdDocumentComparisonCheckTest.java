package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedIdDocumentComparisonCheckTest {

    @Test
    public void shouldBuildSimpleRequestedIdDocumentComparisonCheck() {
        RequestedIdDocumentComparisonCheck result = RequestedIdDocumentComparisonCheck.builder().build();

        assertThat(result, is(instanceOf(RequestedIdDocumentComparisonCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedIdDocumentComparisonConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_COMPARISON"));
    }

}