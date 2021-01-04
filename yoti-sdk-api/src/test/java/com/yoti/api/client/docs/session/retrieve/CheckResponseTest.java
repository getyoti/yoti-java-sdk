package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class CheckResponseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void shouldNotFailWithUnknownType() throws Exception {
        String s = "{ \"type\": \"someUnknownType\", \"id\": \"someId\" }";

        CheckResponse result = MAPPER.readValue(s, CheckResponse.class);
        assertThat(result.getId(), is("someId"));
    }

}
