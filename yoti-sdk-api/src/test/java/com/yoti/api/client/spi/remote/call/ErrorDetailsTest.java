package com.yoti.api.client.spi.remote.call;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

public class ErrorDetailsTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String ERROR_CODE = "AnErrorCode";
    private static final String ERROR_DESCRIPTION = "AnErrorDescription";

    @Test
    public void shouldParseFromJson() {
        Map<String, Object> map = new HashMap<>();
        map.put(Property.ERROR_CODE, ERROR_CODE);
        map.put(Property.DESCRIPTION, ERROR_DESCRIPTION);

        ErrorDetails errorDetails = OBJECT_MAPPER.convertValue(map, ErrorDetails.class);

        assertThat(errorDetails, notNullValue());
        assertThat(errorDetails.getCode(), equalTo(ERROR_CODE));
        assertThat(errorDetails.getDescription(), equalTo(ERROR_DESCRIPTION));
    }

    private static final class Property {

        private static final String ERROR_CODE = "error_code";
        private static final String DESCRIPTION = "description";

        private Property() { }

    }

}
