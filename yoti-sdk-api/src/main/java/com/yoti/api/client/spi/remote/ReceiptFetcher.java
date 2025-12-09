package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Optional;

import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ProfileResponse;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.DecryptionHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiptFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptFetcher.class);

    private final ProfileService profileService;

    private ReceiptFetcher(ProfileService profileService) {
        this.profileService = profileService;
    }

    public static ReceiptFetcher newInstance(KeyPair keyPair, String appId) {
        return new ReceiptFetcher(ProfileService.newInstance(keyPair, appId));
    }

    public Receipt fetch(String encryptedConnectToken, KeyPair keyPair) throws ProfileException {
        LOG.debug("Decrypting connect token: '{}'", encryptedConnectToken);

        String connectToken = decryptConnectToken(encryptedConnectToken, keyPair.getPrivate());
        LOG.debug("Connect token decrypted: '{}'", connectToken);

        ProfileResponse profile = profileService.getProfile(keyPair, connectToken);
        validateReceipt(profile, connectToken);
        return profile.getReceipt();
    }

    private String decryptConnectToken(String encryptedConnectToken, PrivateKey privateKey) throws ProfileException {
        try {
            byte[] byteValue = Base64.getUrlDecoder().decode(encryptedConnectToken);
            byte[] decryptedToken = DecryptionHelper.decryptAsymmetric(byteValue, privateKey);
            return new String(decryptedToken, DEFAULT_CHARSET);
        } catch (Exception ex) {
            throw new ProfileException("Cannot decrypt connect token", ex);
        }
    }

    private void validateReceipt(ProfileResponse profile, String connectToken) throws ProfileException {
        Receipt receipt = Optional.ofNullable(profile)
                .map(ProfileResponse::getReceipt)
                .orElseThrow(() -> new ProfileException("No profile for '" + connectToken + "' was found"));

        if (!receipt.hasOutcome(Receipt.Outcome.SUCCESS)) {
            throw new ActivityFailureException(profile);
        }
    }

}
