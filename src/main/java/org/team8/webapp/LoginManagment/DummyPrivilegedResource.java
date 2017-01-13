package org.team8.webapp.LoginManagment;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
        return "You have general user privilege";
    }

    @RolesAllowed("admin")
    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String displayAdminPrivilegedText(){
        return "You have admin privilege";
    }

    @RolesAllowed("employee")
    @GET
    @Path("/employee")
    @Produces(MediaType.TEXT_PLAIN)
    public String displayEmployeePrivilegedText(){
        return "You have employee privilege";
    }

}
