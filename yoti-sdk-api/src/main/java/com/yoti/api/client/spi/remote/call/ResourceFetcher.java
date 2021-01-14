package com.yoti.api.client.spi.remote.call;

import java.io.IOException;
import java.util.Map;

public interface ResourceFetcher {

    <T> T doRequest(SignedRequest signedRequest, Class<T> resourceClass) throws ResourceException, IOException;

}
