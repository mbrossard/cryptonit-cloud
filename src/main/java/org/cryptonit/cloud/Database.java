package org.cryptonit.cloud;

import java.sql.Connection;
import java.sql.DriverManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.logging.Log;
import org.flywaydb.core.internal.util.logging.LogCreator;
import org.flywaydb.core.internal.util.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Brossard
 */
public class Database {

    private static Logger LOGGER = LoggerFactory.getLogger(Database.class);
    String url = null;
    String user = null;
    String password = null;

    public Database(String url, String user, String password) throws Exception {

        class ourSlf4jLogCreator implements LogCreator {

            public Log createLogger(Class<?> clazz) {
                org.slf4j.Logger l = LoggerFactory.getLogger(clazz);
                if (l instanceof ch.qos.logback.classic.Logger) {
                    ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) l;
                    logbackLogger.setLevel(ch.qos.logback.classic.Level.INFO);
                }
                return new org.flywaydb.core.internal.util.logging.slf4j.Slf4jLog(l);
            }
        }
        LogFactory.setLogCreator(new ourSlf4jLogCreator());

        this.url = url;
        this.user = user;
        this.password = password;

        Class.forName("org.postgresql.Driver");
        Connection c = getConnection();

        Flyway flyway = new Flyway();
        flyway.setDataSource(url, user, password);
        flyway.migrate();
    }

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
