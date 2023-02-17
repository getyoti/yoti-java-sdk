package com.yoti.api.client.spi.remote.http;

import static com.yoti.api.client.spi.remote.call.YotiConstants.SIGNATURE_ALGORITHM;
import static com.yoti.api.client.spi.remote.util.Validation.notMissing;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;
import java.util.Optional;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Crypto {

    public static Builder forPrivateKey(PrivateKey pk) {
        return new Builder(pk);
    }

    public static final class Builder {

        private final PrivateKey privateKey;

        private HttpMethod httpMethod;
        private String path;
        private byte[] body;

        public Builder(PrivateKey pk) {
            privateKey = notNull(pk, Property.PRIVATE_KEY);
        }

        public Builder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = notNull(httpMethod, Property.METHOD);
            return this;
        }

        public Builder path(String path) {
            this.path = notNull(path, Property.PATH);
            return this;
        }

        public Builder body(byte[] body) {
            this.body = notNull(body, Property.BODY);
            return this;
        }

        public String sign() throws GeneralSecurityException {
            notMissing(privateKey, Property.PRIVATE_KEY);
            notMissing(httpMethod, Property.METHOD);
            notMissing(path, Property.PATH);

            StringBuilder msgBuilder = new StringBuilder(httpMethod.name()).append("&").append(path);

            Optional.ofNullable(body)
                    .filter(b -> b.length > 0)
                    .map(b -> msgBuilder.append("&").append(Base64.getEncoder().encodeToString(b)));

            return signMessage(privateKey, msgBuilder.toString().getBytes(StandardCharsets.UTF_8));
        }

        private static String signMessage(PrivateKey privateKey, byte[] message) throws GeneralSecurityException {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            signature.initSign(privateKey, new SecureRandom());
            signature.update(message);
            return Base64.getEncoder().encodeToString(signature.sign());
        }

    }

    private static final class Property {

        private static final String BODY = "Body";
        private static final String PATH = "Path";
        private static final String METHOD = "Http Method";
        private static final String PRIVATE_KEY = "Private Key";

        private Property() { }

    }

}
