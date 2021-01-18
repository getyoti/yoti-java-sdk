package com.yoti.api.client;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DateTimeParameterizedTest {

    private final String input;
    private final String expected;

    public DateTimeParameterizedTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{0} parses to {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "2006-01-02T22:04:05Z", "DateTime{date=2006-01-02,time=22:04:05.000000}" },
                { "2006-01-02T22:04:05.1Z", "DateTime{date=2006-01-02,time=22:04:05.100000}" },
                { "2006-01-02T22:04:05.12Z", "DateTime{date=2006-01-02,time=22:04:05.120000}" },
                { "2006-01-02T22:04:05.123Z", "DateTime{date=2006-01-02,time=22:04:05.123000}" },
                { "2006-01-02T22:04:05.1234Z", "DateTime{date=2006-01-02,time=22:04:05.123400}" },
                { "2006-01-02T22:04:05.12345Z", "DateTime{date=2006-01-02,time=22:04:05.123450}" },
                { "2006-01-02T22:04:05.123456Z", "DateTime{date=2006-01-02,time=22:04:05.123456}" },
                { "2002-10-02T10:00:00-05:00", "DateTime{date=2002-10-02,time=15:00:00.000000}" },
                { "2002-10-02T10:00:00+11:00", "DateTime{date=2002-10-01,time=23:00:00.000000}" }
        });
    }

    @Test
    public void shouldCorrectlyParseDateTimeFromString() {
        DateTime dateTime = DateTime.from(input);
        assertThat(dateTime.toString(), is(expected));
    }

}
