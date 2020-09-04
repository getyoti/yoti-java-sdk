package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedIdDocumentComparisonCheckBuilderTest {

    @Test
    public void shouldBuildSimpleRequestedIdDocumentComparisonCheck() {
        RequestedCheck result = new SimpleRequestedIdDocumentComparisonCheckBuilder().build();

        assertThat(result, is(instanceOf(SimpleRequestedIdDocumentComparisonCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(SimpleRequestedIdDocumentComparisonConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_COMPARISON"));
    }

}