package org.cryptonit.cloud.timestamping;

import javax.ws.rs.Path;
import org.cryptonit.cloud.interfaces.TimestampingAuthorityFactory;

/**
 * @author Mathias Brossard
 */
@Path("/timestamp")
public class Application {
    private static TimestampingAuthorityFactory tsaFactory = null;

    public static void setTimestampingAuthorityFactory(TimestampingAuthorityFactory factory) {
        tsaFactory = factory;
    }
}
