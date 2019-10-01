package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedDocumentAuthenticityCheckBuilderTest {

    @Test
    public void shouldBuildSimpleRequestDocumentAuthenticityCheck() {
        RequestedCheck result = new SimpleRequestedDocumentAuthenticityCheckBuilder()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedDocumentAuthenticityCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(SimpleRequestedDocumentAuthenticityConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_AUTHENTICITY"));
    }

}
