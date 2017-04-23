package org.cryptonit.cloud.timestamping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.cryptonit.cloud.interfaces.TimestampingAuthority;
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
    @Consumes("application/timestamp-query")
    @Produces("application/timestamp-reply")
    public Response timestamp(@Context HttpServletRequest request,
            @Context HttpHeaders headers) throws IOException, TSPException {
        TimeStampRequest tsq = new TimeStampRequest(request.getInputStream());

        TimestampingAuthority tsa = tsaFactory.getTimestampingAuthority(request.getServerName());
        TimeStampResponse tsr = tsa.timestamp(tsq);

        return Response.ok(tsr.getEncoded()).build();
    }

    public static void setTimestampingAuthorityFactory(TimestampingAuthorityFactory factory) {
        tsaFactory = factory;
    }
}
