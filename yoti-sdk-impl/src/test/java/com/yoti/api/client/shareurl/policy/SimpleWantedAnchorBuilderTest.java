package com.yoti.api.client.shareurl.policy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleWantedAnchorBuilderTest {

    private static final String SOME_VALUE = "someAnchorValue";
    private static final String SOME_SUB_TYPE = "someAnchorSubType";

    @Test
    public void shouldBuildWantedAnchorWithGivenValues() {
        WantedAnchor wantedAnchor = new SimpleWantedAnchorBuilder()
                .withValue(SOME_VALUE)
                .withSubType(SOME_SUB_TYPE)
                .build();

        assertEquals(wantedAnchor.getValue(), SOME_VALUE);
        assertEquals(wantedAnchor.getSubType(), SOME_SUB_TYPE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForValueWithNull() {
        new SimpleWantedAnchorBuilder()
                .withValue(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForValueWithEmptyString() {
        new SimpleWantedAnchorBuilder()
                .withValue("")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForSubTypeWithNull() {
        new SimpleWantedAnchorBuilder()
                .withValue(SOME_VALUE)
                .withSubType(null)
                .build();
    }

}
