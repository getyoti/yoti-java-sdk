package com.yoti.api.client.spi.remote;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DocumentDetailsAttributeParserConsecutiveSpacesTest {

    @Parameterized.Parameters(name = "{index}: Test with {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"PASSPORT  GBR 1234abc"},
                {"PASSPORT GBR  1234abc"},
                {"DRIVING_LICENCE GBR 1234abc  2016-05-01 DVLA"},
                {"DRIVING_LICENCE GBR 1234abc 2016-05-01  DVLA"}
        });
    }

    private String testValue;

    public DocumentDetailsAttributeParserConsecutiveSpacesTest(String testValue) {
        this.testValue = testValue;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailForMoreThanoneConsecutiveSpace() throws Exception {
        new DocumentDetailsAttributeParser().parseFrom(testValue);
    }

}
