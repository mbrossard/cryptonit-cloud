package org.cryptonit.cloud.keystore;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.cryptonit.cloud.Database;
import org.cryptonit.cloud.interfaces.KeyStore;

public class SqlKeyStore implements KeyStore {
    Database database;

    public SqlKeyStore(Database database) throws Exception {
        this.database = database;
    }

    private String generateKeyPair(String domain, KeyParameters params) throws Exception {
            return null;
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
