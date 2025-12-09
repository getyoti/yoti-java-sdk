package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Base64;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.DocsSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.ProfileSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    private static final Receipt RECEIPT = new Receipt.Builder()
            .withProfile(new byte[] { 1, 2, 3, 4 })
            .build();

    private static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse.ProfileResponseBuilder()
            .setReceipt(RECEIPT)
            .setSessionData("1234")
            .build();
    private static final String APP_ID = "test-app";
    private static final String TOKEN = "test-token";
    private static final String GENERATED_PROFILE_PATH = "generatedProfilePath";
    private static String B64_PUBLIC_KEY;
    private static KeyPair KEY_PAIR;

    @Spy @InjectMocks ProfileService testObj;

    @Mock UnsignedPathFactory unsignedPathFactory;

    @Mock ProfileSignedRequestStrategy profileSignedRequestStrategyMock;
    @Mock YotiHttpRequest yotiHttpRequest;

    @BeforeClass
    public static void setUpClass() throws Exception {
        KEY_PAIR = generateKeyPairFrom(KEY_PAIR_PEM);
        B64_PUBLIC_KEY = Base64.getEncoder().encodeToString(KEY_PAIR.getPublic().getEncoded());
    }

    @Before
    public void setUp() {
        when(unsignedPathFactory.createProfilePath(TOKEN)).thenReturn(GENERATED_PROFILE_PATH);
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        doReturn(yotiHttpRequest).when(testObj).createSignedRequest(GENERATED_PROFILE_PATH, B64_PUBLIC_KEY);
        when(yotiHttpRequest.execute(ProfileResponse.class)).thenReturn(PROFILE_RESPONSE);

        Receipt result = testObj.getReceipt(KEY_PAIR, TOKEN);

        assertSame(RECEIPT, result);
    }

    @Test
    public void shouldThrowExceptionForIOError() throws Exception {
        IOException ioException = new IOException("Test exception");
        doReturn(yotiHttpRequest).when(testObj).createSignedRequest(GENERATED_PROFILE_PATH, B64_PUBLIC_KEY);
        when(yotiHttpRequest.execute(ProfileResponse.class)).thenThrow(ioException);

        try {
            testObj.getReceipt(KEY_PAIR, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(ioException, e.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws Throwable {
        ResourceException resourceException = new ResourceException(404, "Not Found", "Test exception");
        doReturn(yotiHttpRequest).when(testObj).createSignedRequest(GENERATED_PROFILE_PATH, B64_PUBLIC_KEY);
        when(yotiHttpRequest.execute(ProfileResponse.class)).thenThrow(resourceException);

        try {
            testObj.getReceipt(KEY_PAIR, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(resourceException, e.getCause());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws Exception {
        testObj.getReceipt(null, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullConnectToken() throws Exception {
        testObj.getReceipt(KEY_PAIR, null);
    }

}
