package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedFaceMatchCheckBuilderTest {

    @Test
    public void shouldBuildWithManualCheckAlways() {
        RequestedCheck result = new SimpleRequestedFaceMatchCheckBuilder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedFaceMatchCheck.class)));
        assertThat(result.getConfig(), instanceOf(SimpleRequestedFaceMatchConfig.class));
        assertThat(result.getType(), is("ID_DOCUMENT_FACE_MATCH"));

        SimpleRequestedFaceMatchConfig configResult = (SimpleRequestedFaceMatchConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildWithManualCheckFallback() {
        RequestedCheck result = new SimpleRequestedFaceMatchCheckBuilder()
                .withManualCheckFallback()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedFaceMatchCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(SimpleRequestedFaceMatchConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_FACE_MATCH"));

        SimpleRequestedFaceMatchConfig configResult = (SimpleRequestedFaceMatchConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildWithManualCheckNever() {
        RequestedCheck result = new SimpleRequestedFaceMatchCheckBuilder()
                .withManualCheckNever()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedFaceMatchCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(SimpleRequestedFaceMatchConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_FACE_MATCH"));

        SimpleRequestedFaceMatchConfig configResult = (SimpleRequestedFaceMatchConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullManualCheckType() {
        new SimpleRequestedFaceMatchCheckBuilder().build();
    }

}
