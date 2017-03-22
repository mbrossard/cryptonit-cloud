package org.cryptonit.cloud.timestamping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
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

    @POST
    public Response timestamp(@Context HttpServletRequest request,
            @Context HttpHeaders headers) throws IOException {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public static void setTimestampingAuthorityFactory(TimestampingAuthorityFactory factory) {
        tsaFactory = factory;
    }
}
