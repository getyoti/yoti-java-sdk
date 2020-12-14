package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.Base64.base64;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.UnsupportedEncodingException;

class MessageFactory {

    byte[] create(String httpMethod, String path, byte[] body) {
        try {
            String message = httpMethod + "&" + path;
            if (body != null && body.length > 0) {
                message += "&" + base64(body);
            }
            return message.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            // should not happen. JVM spec mandates UTF-8 support.
            throw new RuntimeException("Unable to convert string to bytes", e);
        }
    }

}
