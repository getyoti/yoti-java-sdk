package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedFaceMatchCheckTest {

    @Test
    public void shouldBuildWithManualCheckAlways() {
        RequestedFaceMatchCheck result = RequestedFaceMatchCheck.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(RequestedFaceMatchCheck.class)));
        assertThat(result.getConfig(), instanceOf(RequestedFaceMatchConfig.class));
        assertThat(result.getType(), is("ID_DOCUMENT_FACE_MATCH"));

        RequestedFaceMatchConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildWithManualCheckFallback() {
        RequestedFaceMatchCheck result = RequestedFaceMatchCheck.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result, is(instanceOf(RequestedFaceMatchCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedFaceMatchConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_FACE_MATCH"));

        RequestedFaceMatchConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildWithManualCheckNever() {
        RequestedFaceMatchCheck result = RequestedFaceMatchCheck.builder()
                .withManualCheckNever()
                .build();

        assertThat(result, is(instanceOf(RequestedFaceMatchCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedFaceMatchConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_FACE_MATCH"));

        RequestedFaceMatchConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullManualCheckType() {
        RequestedFaceMatchCheck.builder()
                .build();
    }

}
