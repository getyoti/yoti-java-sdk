package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.hamcrest.Matchers;
import org.junit.Test;

public class RequestedProfileDocumentMatchCheckTest {

    @Test
    public void shouldBuildWithManualCheckAlways() {
        RequestedProfileDocumentMatchCheck result = RequestedProfileDocumentMatchCheck.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedProfileDocumentMatchCheck.class)));
        assertThat(result.getConfig(), instanceOf(RequestedProfileDocumentMatchConfig.class));
        assertThat(result.getType(), Matchers.is("PROFILE_DOCUMENT_MATCH"));

        RequestedProfileDocumentMatchConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("ALWAYS"));
    }

    @Test
    public void shouldBuildWithManualCheckFallback() {
        RequestedProfileDocumentMatchCheck result = RequestedProfileDocumentMatchCheck.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedProfileDocumentMatchCheck.class)));
        assertThat(result.getConfig(), Matchers.is(instanceOf(RequestedProfileDocumentMatchConfig.class)));
        assertThat(result.getType(), Matchers.is("PROFILE_DOCUMENT_MATCH"));

        RequestedProfileDocumentMatchConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("FALLBACK"));
    }

    @Test
    public void shouldBuildWithManualCheckNever() {
        RequestedProfileDocumentMatchCheck result = RequestedProfileDocumentMatchCheck.builder()
                .withManualCheckNever()
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedProfileDocumentMatchCheck.class)));
        assertThat(result.getConfig(), Matchers.is(instanceOf(RequestedProfileDocumentMatchConfig.class)));
        assertThat(result.getType(), Matchers.is("PROFILE_DOCUMENT_MATCH"));

        RequestedProfileDocumentMatchConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("NEVER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullManualCheckType() {
        RequestedProfileDocumentMatchCheck.builder()
                .build();
    }

}