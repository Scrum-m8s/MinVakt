package org.team8.webapp.LoginManagment;

import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import java.util.Arrays;

import static org.team8.webapp.LoginManagment.Hash.hashPassword;

/**
 * Created by espen on 11.01.2017.
 */

 @Path("/login")
public class LoginCheck {

    public UserDAO dbUser = new UserDAO();

    // Tar brukernavn og passord, skal returnere token hvis lykkes, ellers -1
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int login(User user) {

        User databaseUser = dbUser.getUserById(user.getUserId());

        if (databaseUser == null) {
            return -1;
        } else {

            String hashAndSalt = databaseUser.getPassword();
            byte[] hash = DatatypeConverter.parseHexBinary(hashAndSalt.substring(0, 64));
            byte[] salt = DatatypeConverter.parseHexBinary(hashAndSalt.substring(64));

            byte[] passwordHash = hashPassword(user.getPassword().toCharArray(), salt);

            if(!Arrays.equals(hash, passwordHash)){
                return -1;
            }else{
                // TODO: generer token
                return 123;
            }
        }
    }

    public static void main(String[] args){
        LoginCheck l = new LoginCheck();
        User u = new User();
        u.setUserId("canders");
        u.setPassword("Anders123");
        int i = l.login(u);
        System.out.println(i);
    }
}
