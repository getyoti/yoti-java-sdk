package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class SimpleCheckResponseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void shouldNotFailWithUnknownType() throws Exception {
        String s = "{ \"type\": \"someUnknownType\", \"id\": \"someId\" }";

        SimpleCheckResponse result = MAPPER.readValue(s, SimpleCheckResponse.class);
        assertThat(result.getId(), is("someId"));
    }

}
