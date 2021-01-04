package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class RequestedDocumentAuthenticityCheckTest {

    @Test
    public void shouldBuildRequestedDocumentAuthenticityCheck() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .build();

        assertThat(result, is(instanceOf(RequestedDocumentAuthenticityCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedDocumentAuthenticityConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_AUTHENTICITY"));
        assertThat(result.getConfig().getManualCheck(), is(nullValue()));
    }

    @Test
    public void withManualCheckAlways_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void withManualCheckFallback_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void withManualCheckNever_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withManualCheckNever()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("NEVER"));
    }

}
