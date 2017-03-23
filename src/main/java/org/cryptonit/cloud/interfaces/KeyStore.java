package org.cryptonit.cloud.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyStore {
    public enum KeyParameters {
        RSA_2048("RSA", null, 2048),
        RSA_4096("RSA", null, 4096),
        EC_P256("EC", "P-256", 256),
        EC_P384("EC", "P-384", 384),
        EC_P521("EC", "P-521", 521);

        final private String algorithm;
        final private String parameter;
        final private int size;

        KeyParameters(String algorithm, String parameter, int size) {
            this.algorithm = algorithm;
            this.parameter = parameter;
            this.size = size;
        }
        
        public String getAlgorithm() {
            return algorithm;
        }

        public String getParameter() {
            return parameter;
        }

        public int getSize() {
            return size;
        }
    };

    String generateKey(String domain, KeyParameters params);

    PrivateKey getPrivateKey(String domain, String keyIdentifier);

    PublicKey getPublicKey(String domain, String keyIdentifier);
}
