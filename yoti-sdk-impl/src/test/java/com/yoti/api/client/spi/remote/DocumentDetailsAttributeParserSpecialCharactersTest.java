package com.yoti.api.client.spi.remote;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.yoti.api.client.DocumentDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class DocumentDetailsAttributeParserSpecialCharactersTest {

    @Parameterized.Parameters(name = "{index}: Test with {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"****"},
                {"~!@#$%^&*()-_=+[]{}|;':,./<>?"},
                {"\"\""},
                {"\\"},
                {"\""},
                {"''"},
                {"'"}
        });
    }

    private String testValue;

    public DocumentDetailsAttributeParserSpecialCharactersTest(String testValue) {
        this.testValue = testValue;
    }

    @Test
    public void shouldParseDocumentDetailsWithSpecialCharacters() throws Exception {
        DocumentDetails result = new DocumentDetailsAttributeParser()
                .parseFrom(String.format("PASS_CARD GBR %s - DVLA", testValue));

        assertThat(result.getDocumentNumber(), is(testValue));
    }

}
