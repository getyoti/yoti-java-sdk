package com.yoti.api.client.spi.remote.call.factory;

import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.verifyMessage;

public class SignatureFactoryTest {

    private static final String SOME_MESSAGE = "someMessage";

    SignatureFactory testObj = new SignatureFactory();

    KeyPair keyPair;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
    }

    @Test
    public void shouldCreate() throws Exception {
        byte[] result = testObj.create(SOME_MESSAGE.getBytes(), keyPair.getPrivate());

        verifyMessage(SOME_MESSAGE.getBytes(), keyPair.getPublic(), result);
    }

}
