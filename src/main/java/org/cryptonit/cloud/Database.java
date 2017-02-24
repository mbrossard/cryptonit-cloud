package org.cryptonit.cloud;

import java.sql.Connection;
import java.sql.DriverManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.logging.Log;
import org.flywaydb.core.internal.util.logging.LogCreator;
import org.flywaydb.core.internal.util.logging.LogFactory;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Brossard
 */
public class Database {

    public Database(String url, String user, String password) {
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

        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
        }

        Flyway flyway = new Flyway();
        flyway.setDataSource(url, user, password);
        flyway.migrate();
    }
}
