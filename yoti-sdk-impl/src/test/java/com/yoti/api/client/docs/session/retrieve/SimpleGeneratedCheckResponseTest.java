package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class SimpleGeneratedCheckResponseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String testObj = "{ \"type\": \"someUnknownType\" }";

    @Test
    public void shouldNotThrowExceptionOnUnknownType() throws Exception {
        SimpleGeneratedCheckResponse result = MAPPER.readValue(testObj, SimpleGeneratedCheckResponse.class);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(SimpleGeneratedCheckResponse.class)));
    }

}
