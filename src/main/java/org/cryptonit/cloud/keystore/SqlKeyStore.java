package org.cryptonit.cloud.keystore;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import org.cryptonit.cloud.Database;
import org.cryptonit.cloud.interfaces.KeyStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlKeyStore implements KeyStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlKeyStore.class);
    private static final SecureRandom random = new SecureRandom();
    Database database;

    public SqlKeyStore(Database database) throws Exception {
        this.database = database;
    }

    private String generateKeyPair(String domain, KeyParameters params) throws Exception {
        KeyFactory keyFactory;
        KeyPair kp;
        if(params.getAlgorithm().equalsIgnoreCase("RSA")) {
            keyFactory = KeyFactory.getInstance("RSA", "BC");
            KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            rsaKeyPairGenerator.initialize(params.getSize(), random);
            kp = rsaKeyPairGenerator.generateKeyPair();
        } else {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(kp.getPublic().getEncoded());
        byte[] digest = md.digest();

        String keyId = String.format("%064x", new java.math.BigInteger(1, digest));

        return keyId;
    }
    
    @Override
    public String generateKey(String domain, KeyParameters params) {
        String r = null;
        try {
            r = generateKeyPair(domain, params);
        } catch(Exception e) {
            LOGGER.error("Error generating key", e);
        }
        return r;
    }

    @Override
    public PrivateKey getPrivateKey(String domain, String keyIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PublicKey getPublicKey(String domain, String keyIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
