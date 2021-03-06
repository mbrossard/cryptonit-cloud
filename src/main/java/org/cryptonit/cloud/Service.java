package org.cryptonit.cloud;

import java.net.URL;
import java.security.Security;
import org.cryptonit.cloud.interfaces.KeyStore;
import org.cryptonit.cloud.keystore.SqlKeyStore;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Brossard
 */
public class Service {
    public static final String WEBAPP_RESOURCES_LOCATION = "webapp";
    private static final String DEFAULT_PORT_START = "8080";
    private static final String DEFAULT_DB_USER = "cryptonit";
    private static final String DEFAULT_DB_PASSWORD = "cryptonit";
    private static Logger LOGGER = LoggerFactory.getLogger(Service.class);
    private Database database = null;

    public Service(Database database) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("org.eclipse.jetty");
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
            logbackLogger.setLevel(ch.qos.logback.classic.Level.INFO);
        }

        this.database = database;
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("http.port", DEFAULT_PORT_START));
        String url = System.getProperty("db.url", "");
        String user = System.getProperty("db.user", DEFAULT_DB_USER);
        String password = System.getProperty("db.password", DEFAULT_DB_PASSWORD);

        
        if(url == null || url.length() == 0) {
            System.err.println("No database connection provided");
            return;
        }

        Database db = new Database(url, user, password);
        Service service = new Service(db);
        service.start(port);
    }

    public void start(int port) throws Exception {
        Server server = new Server(port);
        WebAppContext root = new WebAppContext();

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        ExecutionContext context = new ExecutionContext(database, new SqlKeyStore(database));

        // Serve index.html
        Application.addClass(Index.class);

        // Timestamping application
        org.cryptonit.cloud.timestamping.Service.register(context);

        root.setContextPath("/");
        root.setDescriptor(WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml");

        URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
        if (webAppDir == null) {
            throw new RuntimeException(String.format("No %s directory was found into the JAR file", WEBAPP_RESOURCES_LOCATION));
        }
        root.setResourceBase(webAppDir.toURI().toString());
        root.setParentLoaderPriority(true);
        server.setHandler(root);
        try {
            server.start();
            LOGGER.info("Server started");
            server.join();
        } finally {
            server.destroy();
        }
        LOGGER.info("Server exited");
    }
}
