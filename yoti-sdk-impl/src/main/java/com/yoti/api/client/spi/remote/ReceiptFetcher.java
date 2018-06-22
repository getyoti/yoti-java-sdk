package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.security.KeyPair;
import java.security.PrivateKey;

import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.RemoteProfileService;
import com.yoti.api.client.spi.remote.util.DecryptionHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReceiptFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptFetcher.class);

    private final ProfileService profileService;

    private ReceiptFetcher(ProfileService profileService) {
        this.profileService = profileService;
    }

    static ReceiptFetcher newInstance() {
        return new ReceiptFetcher(RemoteProfileService.newInstance());
    }

    Receipt fetch(String encryptedConnectToken, KeyPair keyPair, String appId) throws ProfileException {
        LOG.debug("Decrypting connect token: '{}'", encryptedConnectToken);
        String connectToken = decryptConnectToken(encryptedConnectToken, keyPair.getPrivate());
        LOG.debug("Connect token decrypted: '{}'", connectToken);
        Receipt receipt = profileService.getReceipt(keyPair, appId, connectToken);
        validateReceipt(receipt, connectToken);
        return receipt;
    }

    private String decryptConnectToken(String encryptedConnectToken, PrivateKey privateKey) throws ProfileException {
        try {
            byte[] byteValue = Base64.getUrlDecoder().decode(encryptedConnectToken);
            byte[] decryptedToken = DecryptionHelper.decryptAsymmetric(byteValue, privateKey);
            return new String(decryptedToken, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new ProfileException("Cannot decrypt connect token", e);
        }
    }

    private void validateReceipt(Receipt receipt, String connectToken) throws ProfileException {
        if (receipt == null) {
            throw new ProfileException("No receipt for '" + connectToken + "' was found");
        }
        if (receipt.getOutcome() == null || !receipt.getOutcome().isSuccessful()) {
            throw new ActivityFailureException("Sharing activity unsuccessful for " + receipt.getDisplayReceiptId());
        }
    }

}
