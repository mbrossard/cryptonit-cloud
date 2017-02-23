package org.cryptonit.cloud;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Mathias Brossard
 */
public class Database {

    public Database(String url, String user, String password) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
        }
    }
}
