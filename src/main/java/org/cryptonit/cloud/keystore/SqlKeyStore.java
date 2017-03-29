package org.cryptonit.cloud.keystore;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Base64;
import org.bouncycastle.jce.ECNamedCurveTable;
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
        } else if (params.getAlgorithm().equalsIgnoreCase("EC")) {
            KeyPairGenerator ecKeyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyFactory = KeyFactory.getInstance("EC", "BC");
            ecKeyPairGenerator.initialize(ECNamedCurveTable.getParameterSpec(params.getParameter()), random);
            kp = ecKeyPairGenerator.generateKeyPair();
        } else {
            return null;
        }

        byte[] buf = keyFactory.getKeySpec(kp.getPrivate(), PKCS8EncodedKeySpec.class).getEncoded();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(kp.getPublic().getEncoded());
        byte[] digest = md.digest();

        String keyId = String.format("%064x", new java.math.BigInteger(1, digest));

        Connection c = database.getConnection();
        CallableStatement cs = c.prepareCall("INSERT INTO keystore(domain, keyId, type, created, private, public) " + 
                "VALUES (?, ?, ?, NOW(), ?, ?)");
        cs.setString(1, domain);
        cs.setString(2, keyId);
        cs.setString(3, params.getAlgorithm());
        cs.setString(4, Base64.getEncoder().encodeToString(buf));
        cs.setString(5, Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()));
        cs.execute();

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
