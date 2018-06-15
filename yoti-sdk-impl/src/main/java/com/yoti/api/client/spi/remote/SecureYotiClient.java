package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.KeyPairSource.StreamVisitor;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.aml.AmlResult;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YotiClient talking to the Yoti Connect API remotely.
 */
final class SecureYotiClient implements YotiClient {

    private static final Logger LOG = LoggerFactory.getLogger(SecureYotiClient.class);

    private final String appId;
    private final KeyPair keyPair;
    private final ReceiptFetcher receiptFetcher;
    private final RemoteAmlService remoteAmlService;
    private final ActivityDetailsFactory activityDetailsFactory;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    SecureYotiClient(String applicationId,
            KeyPairSource kpSource,
            ReceiptFetcher receiptFetcher,
            ActivityDetailsFactory activityDetailsFactory,
            RemoteAmlService remoteAmlService) throws InitialisationException {
        this.appId = notNull(applicationId, "Application id");
        this.keyPair = loadKeyPair(notNull(kpSource, "Key pair source"));
        this.receiptFetcher = notNull(receiptFetcher, "receiptFetcher");
        this.remoteAmlService = notNull(remoteAmlService, "amlService");
        this.activityDetailsFactory = notNull(activityDetailsFactory, "activityDetailsFactory");
    }

    @Override
    public ActivityDetails getActivityDetails(String encryptedConnectToken) throws ProfileException {
        Receipt receipt = receiptFetcher.fetch(encryptedConnectToken, keyPair, appId);
        return activityDetailsFactory.create(receipt, keyPair.getPrivate());
    }

    @Override
    public AmlResult performAmlCheck(AmlProfile amlProfile) throws AmlException {
        LOG.debug("Performing aml check...");
        return remoteAmlService.performCheck(keyPair, appId, amlProfile);
    }

    private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            LOG.debug("Loading key pair from '{}'", kpSource);
            return kpSource.getFromStream(new KeyStreamVisitor());
        } catch (IOException e) {
            throw new InitialisationException("Cannot load key pair", e);
        }
    }

    private static class KeyStreamVisitor implements StreamVisitor {

        @Override
        public KeyPair accept(InputStream stream) throws IOException, InitialisationException {
            PEMParser reader = new PEMParser(new BufferedReader(new InputStreamReader(stream, DEFAULT_CHARSET)));
            KeyPair keyPair = findKeyPair(reader);
            if (keyPair == null) {
                throw new InitialisationException("No key pair found in the provided source");
            }
            return keyPair;
        }

        private KeyPair findKeyPair(PEMParser reader) throws IOException {
            for (Object o; (o = reader.readObject()) != null; ) {
                if (o instanceof PEMKeyPair) {
                    return new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) o);
                }
            }
            return null;
        }

    }

}
