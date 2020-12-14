package com.yoti.api.client.shareurl.policy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WantedAnchorTest {

    private static final String SOME_VALUE = "someAnchorValue";
    private static final String SOME_SUB_TYPE = "someAnchorSubType";

    @Test
    public void shouldBuildWantedAnchorWithGivenValues() {
        WantedAnchor wantedAnchor = WantedAnchor.builder()
                .withValue(SOME_VALUE)
                .withSubType(SOME_SUB_TYPE)
                .build();

        assertEquals(SOME_VALUE, wantedAnchor.getValue());
        assertEquals(SOME_SUB_TYPE, wantedAnchor.getSubType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForValueWithNull() {
        WantedAnchor.builder()
                .withValue(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForValueWithEmptyString() {
        WantedAnchor.builder()
                .withValue("")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForSubTypeWithNull() {
        WantedAnchor.builder()
                .withValue(SOME_VALUE)
                .withSubType(null)
                .build();
    }

}
