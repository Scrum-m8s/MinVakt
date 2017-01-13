package org.team8.webapp.LoginManagment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by espen on 12.01.2017.
 */

@Path("/dummy")
public class DummyPrivilegedResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String displayPrivilegedText(){
        return "Secret";
    }
}
