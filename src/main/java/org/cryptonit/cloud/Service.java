package org.cryptonit.cloud;

import java.net.URL;
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
    private static Logger LOGGER;

    public Service() {
        LOGGER = LoggerFactory.getLogger(Service.class);
    }
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("http.port", DEFAULT_PORT_START));
        Service server = new Service();
        server.start(port);
    }

    public void start(int port) throws Exception {
        Server server = new Server(port);
        WebAppContext root = new WebAppContext();

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