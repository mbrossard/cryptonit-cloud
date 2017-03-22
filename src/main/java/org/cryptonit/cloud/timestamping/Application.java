package org.cryptonit.cloud.timestamping;

import javax.ws.rs.Path;
import org.cryptonit.cloud.interfaces.TimestampingAuthorityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Brossard
 */
@Path("/timestamp")
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static TimestampingAuthorityFactory tsaFactory = null;

    public static void setTimestampingAuthorityFactory(TimestampingAuthorityFactory factory) {
        tsaFactory = factory;
    }
}
