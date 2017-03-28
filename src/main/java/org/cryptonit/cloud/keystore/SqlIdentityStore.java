package org.cryptonit.cloud.keystore;

import org.bouncycastle.asn1.x500.X500Name;
import org.cryptonit.cloud.Database;
import org.cryptonit.cloud.interfaces.IdentityStore;
import org.cryptonit.cloud.interfaces.KeyStore;

public class SqlIdentityStore implements IdentityStore {
    Database database;
    KeyStore keyStore;

    public SqlIdentityStore(Database database, KeyStore keyStore) {
        this.database = database;
        this.keyStore = keyStore;
    }

    @Override
    public String newIdentity(String domain, String keyId, X500Name subject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
