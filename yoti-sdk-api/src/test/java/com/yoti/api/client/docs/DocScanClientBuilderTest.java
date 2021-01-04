package com.yoti.api.client.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;

import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.common.StaticKeyPairSource;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.junit.Before;
import org.junit.Test;

public class DocScanClientBuilderTest {

    private static final String SOME_APPLICATION_ID = "someAppId";

    private KeyPairSource validKeyPairSource;

    @Before
    public void setUp() {
        validKeyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
    }

    @Test
    public void build_shouldThrowExceptionWhenSdkIdIsNull() {
        try {
            DocScanClient.builder()
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("SDK ID"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void build_shouldThrowExceptionWhenSdkIdIsEmpty() {
        try {
            DocScanClient.builder()
                    .withClientSdkId("")
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("SDK ID"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void build_shouldThrowExceptionWhenKeyPairSourceIsNull() {
        try {
            DocScanClient.builder()
                    .withClientSdkId(SOME_APPLICATION_ID)
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application key Pair"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void build_shouldCorrectlyBuildDocScanClient() {
        DocScanClient result = DocScanClient.builder()
                .withClientSdkId(SOME_APPLICATION_ID)
                .withKeyPairSource(validKeyPairSource)
                .build();

        assertThat(result, is(notNullValue()));
    }

}
