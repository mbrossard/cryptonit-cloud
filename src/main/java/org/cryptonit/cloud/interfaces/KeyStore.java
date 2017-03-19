package org.cryptonit.cloud.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyStore {
    PrivateKey getPrivateKey(String domain, String keyIdentifier);

    PublicKey getPublicKey(String domain, String keyIdentifier);
}
