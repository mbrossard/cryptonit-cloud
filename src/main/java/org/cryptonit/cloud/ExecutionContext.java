package org.cryptonit.cloud;

public class ExecutionContext {
    Database database;

    public ExecutionContext(Database db) throws Exception {
        this.database = db;
    }
}
