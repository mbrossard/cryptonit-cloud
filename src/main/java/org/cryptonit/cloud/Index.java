package org.cryptonit.cloud;

import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Mathias Brossard
 */
@Path("")
public class Index {

    @GET
    public Response index() throws URISyntaxException {
        URI index = new URI("index.html");
        return Response.temporaryRedirect(index).build();
    }
}
