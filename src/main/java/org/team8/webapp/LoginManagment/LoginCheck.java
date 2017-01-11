package org.team8.webapp.LoginManagment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by espen on 11.01.2017.
 */

 @Path("/login")
public class LoginCheck {

    public LoginDAO login = new LoginDAO();

    // Tar brukernavn og passord, skal returnere token hvis lykkes, ellers -1
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int login(User user){

        if(login.validateLogin(user.getUserName(), user.getPassword())){

            // TODO: generer token

            return 123;
        }else{
            return -1;
        }
    }


}
