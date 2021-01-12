package com.yoti.api.client.spi.remote.util;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ValidationTest {

    private static String SOME_NULL_STRING = null;
    private static List<String> SOME_NULL_LIST = null;

    private static String SOME_VALID_STRING = "someString";

    @Test
    public void notNull_shouldThrowExceptionWhenNullIsPassed() {
        try {
            Validation.notNull(null, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notNull_shouldThrowExceptionWhenNullIsPassedByReference() {
        try {
            Validation.notNull(SOME_NULL_STRING, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notNull_shouldReturnTheValueIfNotNull() {
        String result = Validation.notNull(SOME_VALID_STRING, "theValue");
        assertEquals(SOME_VALID_STRING, result);
    }

    @Test
    public void isNullOrEmpty_shouldReturnTrueWhenPassedNull() {
        boolean result = Validation.isNullOrEmpty(SOME_NULL_STRING);
        assertTrue(result);
    }

    @Test
    public void isNullOrEmpty_shouldReturnTrueWhenPassedEmptyString() {
        boolean result = Validation.isNullOrEmpty("");
        assertTrue(result);
    }

    @Test
    public void isNullOrEmpty_shouldReturnFalseWhenGivenValidString() {
        boolean result = Validation.isNullOrEmpty(SOME_VALID_STRING);
        assertFalse(result);
    }

    @Test
    public void notNullOrEmpty_shouldThrowExceptionWhenGivenNull() {
        try {
            Validation.notNullOrEmpty(SOME_NULL_STRING, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notNullOrEmpty_shouldThrowExceptionWhenGivenEmptyString() {
        try {
            Validation.notNullOrEmpty("", "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notNullOrEmpty_shouldThrowExceptionWhenGivenNullList() {
        try {
            Validation.notNullOrEmpty(SOME_NULL_LIST, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notNullOrEmpty_shouldThrowExceptionWhenGivenEmptyList() {
        try {
            Validation.notNullOrEmpty(Collections.emptyList(), "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notEqualTo_shouldThrowExceptionIfObjectsAreEqual() {
        try {
            Validation.notEqualTo("valueToCompare", "valueToCompare", "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notGreaterThan_shouldThrowExceptionIfValueIsGreaterThanLimit() {
        try {
            Validation.notGreaterThan(3, 2, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void notLessThan_shouldThrowExceptionIfValueIsLessThanLimit() {
        try {
            Validation.notLessThan(3, 4, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void withinRange_shouldThrowExceptionIfValueIsNotWithinRange() {
        try {
            Validation.withinRange(3, 5, 10, "theValue");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void matchesPattern_shouldThrowExceptionIfStringDoesNotMatchRegularExpression() {
        try {
            Validation.matchesPattern("somestring", "([A-Z])\\w+", "theValue"); // Checks for uppercase first letter
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("theValue"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void withinList_shouldThrowExceptionIfValueNotWithinList() {
        try {
            Validation.withinList("Orange", Arrays.asList("Apple", "Pear"));
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Orange"));
            assertThat(ex.getMessage(), containsString("[Apple, Pear]"));
            return;
        }
        fail("Expected an exception");
    }


}
