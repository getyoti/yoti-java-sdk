package com.yoti.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SignedTimestampTest {

    private static final DateTime DATE_TIME_1 = DateTime.from(123456789);
    private static final DateTime DATE_TIME_2 = DateTime.from(987654321);

    @Test
    public void constructor_willNotAllowNullTimestamp() {
        try {
            new SignedTimestamp(1, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("timestamp"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void equals_returnsFalseWhenComparedToNull() {
        SignedTimestamp signedTimestampValue = new SignedTimestamp(1, DATE_TIME_1);

        assertFalse(signedTimestampValue.equals(null));
    }

    @Test
    public void equals_shouldBeReflexive() {
        SignedTimestamp signedTimestampValue = new SignedTimestamp(1, DATE_TIME_1);

        assertTrue(signedTimestampValue.equals(signedTimestampValue));
        assertTrue(signedTimestampValue.hashCode() == signedTimestampValue.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesMatch() {
        SignedTimestamp signedTimestampValue1 = new SignedTimestamp(1, DATE_TIME_1);
        SignedTimestamp signedTimestampValue2 = new SignedTimestamp(1, DATE_TIME_1);

        assertTrue(signedTimestampValue1.equals(signedTimestampValue2));
        assertTrue(signedTimestampValue2.equals(signedTimestampValue1));
        assertTrue(signedTimestampValue1.hashCode() == signedTimestampValue2.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesDoNotMatch() {
        SignedTimestamp signedTimestampValue1 = new SignedTimestamp(1, DATE_TIME_1);
        SignedTimestamp signedTimestampValue2 = new SignedTimestamp(1, DATE_TIME_2);

        assertFalse(signedTimestampValue1.equals(signedTimestampValue2));
        assertFalse(signedTimestampValue2.equals(signedTimestampValue1));
    }

    @Test
    public void equals_shouldBeTransitive() {
        SignedTimestamp signedTimestampValue1 = new SignedTimestamp(1, DATE_TIME_1);
        SignedTimestamp signedTimestampValue2 = new SignedTimestamp(1, DATE_TIME_1);
        SignedTimestamp signedTimestampValue3 = new SignedTimestamp(1, DATE_TIME_1);

        assertTrue(signedTimestampValue1.equals(signedTimestampValue2));
        assertTrue(signedTimestampValue1.equals(signedTimestampValue3));
        assertTrue(signedTimestampValue2.equals(signedTimestampValue3));
        assertTrue(signedTimestampValue1.hashCode() == signedTimestampValue2.hashCode());
        assertTrue(signedTimestampValue2.hashCode() == signedTimestampValue3.hashCode());
    }

}
