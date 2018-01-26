package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.verifyMessage;
import static org.mockito.Mockito.when;

import com.yoti.api.client.spi.remote.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.KeyPair;

@RunWith(MockitoJUnitRunner.class)
public class SignedMessageFactoryTest {

    private static final String SOME_METHOD = "someMethod";
    private static final String SOME_PATH = "somePath";
    private static final String SOME_MESSAGE = "someMessage";
    private static final String SOME_BODY = "someBody";

    @InjectMocks SignedMessageFactory testObj;

    @Mock MessageFactory messageFactoryMock;

    KeyPair keyPair;
    byte[] bodyBytes = SOME_BODY.getBytes();
    byte[] messageBytes = SOME_MESSAGE.getBytes();

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
    }

    @Test
    public void shouldCreateWithoutBody() throws Exception {
        when(messageFactoryMock.create(SOME_METHOD, SOME_PATH, null)).thenReturn(messageBytes);

        String result = testObj.create(keyPair.getPrivate(), SOME_METHOD, SOME_PATH);

        verifyMessage(messageBytes, keyPair.getPublic(), Base64.getDecoder().decode(result));
    }

    @Test
    public void shouldCreateWithBody() throws Exception {
        when(messageFactoryMock.create(SOME_METHOD, SOME_PATH, bodyBytes)).thenReturn(messageBytes);

        String result = testObj.create(keyPair.getPrivate(), SOME_METHOD, SOME_PATH, bodyBytes);

        verifyMessage(messageBytes, keyPair.getPublic(), Base64.getDecoder().decode(result));
    }

}
