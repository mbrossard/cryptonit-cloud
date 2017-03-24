package org.cryptonit.cloud;

import org.cryptonit.cloud.interfaces.KeyStore;

public class ExecutionContext {
    public Database database;
    public KeyStore keyStore;

    public ExecutionContext(Database db, KeyStore ks) throws Exception {
        this.database = db;
        this.keyStore = ks; 
    }
}
