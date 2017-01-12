package org.team8.webapp.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 *
 * @author Mr.Easter
 */
@Path("/users")
public class UserResource {
    UserDAO dao = new UserDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<User> getUsers() {
        System.out.println("getUsers");
        return dao.getUsers();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("id") String id) {
        System.out.println("getUserById: " + id);
        return dao.getUserById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createUser(User e) {
        System.out.println("createUser");
        return dao.createUser(e);
    }

    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateUser(User e) {
        System.out.println("updateUser");
        return dao.updateUser(e);
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeUser(@PathParam("id") String id) {
        dao.removeUser(id);
    }
}