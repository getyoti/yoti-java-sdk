package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.yoti.api.client.DateTime;

import org.junit.Test;

public class SignedTimestampValueTest {

    private static final DateTime DATE_TIME_1 = DateTimeValue.from(123456789);
    private static final DateTime DATE_TIME_2 = DateTimeValue.from(987654321);

    @Test
    public void equals_returnsFalseWhenComparedToNull() {
        SignedTimestampValue signedTimestampValue = new SignedTimestampValue(1, DATE_TIME_1);

        assertFalse(signedTimestampValue.equals(null));
    }

    @Test
    public void equals_shouldBeReflexive() {
        SignedTimestampValue signedTimestampValue = new SignedTimestampValue(1, DATE_TIME_1);

        assertTrue(signedTimestampValue.equals(signedTimestampValue));
        assertTrue(signedTimestampValue.hashCode() == signedTimestampValue.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesMatch() {
        SignedTimestampValue signedTimestampValue1 = new SignedTimestampValue(1, DATE_TIME_1);
        SignedTimestampValue signedTimestampValue2 = new SignedTimestampValue(1, DATE_TIME_1);

        assertTrue(signedTimestampValue1.equals(signedTimestampValue2));
        assertTrue(signedTimestampValue2.equals(signedTimestampValue1));
        assertTrue(signedTimestampValue1.hashCode() == signedTimestampValue2.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesDoNotMatch() {
        SignedTimestampValue signedTimestampValue1 = new SignedTimestampValue(1, DATE_TIME_1);
        SignedTimestampValue signedTimestampValue2 = new SignedTimestampValue(1, DATE_TIME_2);

        assertFalse(signedTimestampValue1.equals(signedTimestampValue2));
        assertFalse(signedTimestampValue2.equals(signedTimestampValue1));
    }

    @Test
    public void equals_shouldBeTransitive() {
        SignedTimestampValue signedTimestampValue1 = new SignedTimestampValue(1, DATE_TIME_1);
        SignedTimestampValue signedTimestampValue2 = new SignedTimestampValue(1, DATE_TIME_1);
        SignedTimestampValue signedTimestampValue3 = new SignedTimestampValue(1, DATE_TIME_1);

        assertTrue(signedTimestampValue1.equals(signedTimestampValue2));
        assertTrue(signedTimestampValue1.equals(signedTimestampValue3));
        assertTrue(signedTimestampValue2.equals(signedTimestampValue3));
        assertTrue(signedTimestampValue1.hashCode() == signedTimestampValue2.hashCode());
        assertTrue(signedTimestampValue2.hashCode() == signedTimestampValue3.hashCode());
    }

}
