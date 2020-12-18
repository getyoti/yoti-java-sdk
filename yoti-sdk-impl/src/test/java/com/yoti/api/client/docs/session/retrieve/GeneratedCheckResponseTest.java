package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class GeneratedCheckResponseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String testObj = "{ \"type\": \"someUnknownType\" }";

    @Test
    public void shouldNotThrowExceptionOnUnknownType() throws Exception {
        GeneratedCheckResponse result = MAPPER.readValue(testObj, GeneratedCheckResponse.class);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(GeneratedCheckResponse.class)));
    }

}
