package com.yoti.api.client;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.Test;

public class ClassPathKeySourceTest {
    @Test
    public void shouldLoadExistingClasspathResource() throws FileNotFoundException {
        ClassPathKeySource keySource = ClassPathKeySource.fromClasspath("testfile");
        assertNotNull(keySource);
    }

    @Test(expected = InitialisationException.class)
    public void shouldLoadNonexistentClasspathResource() throws FileNotFoundException {
        ClassPathKeySource.fromClasspath("testfile-nonexistent");
    }
}
