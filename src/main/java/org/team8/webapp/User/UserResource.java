package org.team8.webapp.User;

import org.team8.webapp.LoginManagment.Hash;
import org.team8.webapp.LoginManagment.LoginCheck;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;

import static org.team8.webapp.LoginManagment.Hash.createHashedPassword;
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

    @Path("/current")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getCurrentUser(@Context SecurityContext sc){
        System.out.println("getCurrentUser");
        return dao.getUserById(sc.getUserPrincipal().getName());
    }

    @Path("current/updatepassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean changePassword(@Context SecurityContext sc, PasswordChange password){

        User u = dao.getUserById(sc.getUserPrincipal().getName());

        if(u == null) return false;


        u.setPassword(password.getOldpassword());
        if(LoginCheck.validateCredentials(u) < 0) return false;

        String hashedPassword = Hash.createHashedPassword(password.getNewpassword());

        u.setPassword(hashedPassword);

        if(dao.updateUser(u)){
            return true;
        }else{
            return false;
        }
    }

    @Path("/isadmin")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String isAdmin(@Context SecurityContext sc){
        User u = dao.getUserById(sc.getUserPrincipal().getName());
        if(u.getRole() == 0){
            return Boolean.TRUE.toString();
        }else return Boolean.FALSE.toString();
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
    public boolean createUser(User e) {
        System.out.println("createUser");
        String hashedPassword = createHashedPassword(e.getPassword());
        e.setPassword(hashedPassword);
        return dao.createUser(e);
    }

 //   @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateUser(User e) {
        System.out.println("updateUser");
        String hashedPassword = createHashedPassword(e.getPassword());
        e.setPassword(hashedPassword);
        return dao.updateUser(e);
    }


    /* TODO:
    @Path("updateRole/{role}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateRole(User e) {
        System.out.println("updateRole");
        return dao.updateRole(e);
    }
    */

    @Path("{id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeUser(@PathParam("id") String id) {
        return dao.removeUser(id);
    }
}
