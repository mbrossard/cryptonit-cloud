package org.cryptonit.cloud.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyStore {
    public enum KeyParameters {
        RSA_2048("RSA", null, 2048),

        final private String algorithm;
        final private String parameter;
        final private int size;

        KeyParameters(String algorithm, String parameter, int size) {
            this.algorithm = algorithm;
            this.parameter = parameter;
            this.size = size;
        }
    };

    PrivateKey getPrivateKey(String domain, String keyIdentifier);

    PublicKey getPublicKey(String domain, String keyIdentifier);
}
