package org.cryptonit.cloud;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.cryptonit.cloud.Service.WEBAPP_RESOURCES_LOCATION;

/**
 * @author Mathias Brossard
 */
@Path("")
public class Index {

    @GET
    public Response index() throws URISyntaxException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION + "/index.html").toURI();
        return Response.ok(new File(uri), MediaType.TEXT_HTML).build();
    }
}
