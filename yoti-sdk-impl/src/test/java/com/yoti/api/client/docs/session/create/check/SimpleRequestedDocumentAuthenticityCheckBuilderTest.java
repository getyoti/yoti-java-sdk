package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class SimpleRequestedDocumentAuthenticityCheckBuilderTest {

    @Test
    public void shouldBuildSimpleRequestDocumentAuthenticityCheck() {
        RequestedDocumentAuthenticityCheck<?> result = new SimpleRequestedDocumentAuthenticityCheckBuilder()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedDocumentAuthenticityCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(SimpleRequestedDocumentAuthenticityConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_AUTHENTICITY"));
        assertThat(result.getConfig().getManualCheck(), is(nullValue()));
    }

    @Test
    public void withManualCheckAlways_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck<?> result = new SimpleRequestedDocumentAuthenticityCheckBuilder()
                .withManualCheckAlways()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void withManualCheckFallback_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck<?> result = new SimpleRequestedDocumentAuthenticityCheckBuilder()
                .withManualCheckFallback()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void withManualCheckNever_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck<?> result = new SimpleRequestedDocumentAuthenticityCheckBuilder()
                .withManualCheckNever()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("NEVER"));
    }

}
