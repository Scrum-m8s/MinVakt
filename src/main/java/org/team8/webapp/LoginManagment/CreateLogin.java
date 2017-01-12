package org.team8.webapp.LoginManagment;

import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import static org.team8.webapp.LoginManagment.Hash.generateSalt;
import static org.team8.webapp.LoginManagment.Hash.hashPassword;

/**
 * Created by espen on 11.01.2017.
 */

@Path("/signup")
public class CreateLogin {

    public UserDAO dbUser = new UserDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int signUp(User user){

        String userName = user.getUserId();
        String password = user.getPassword();

        // Hvis brukernavn allerede eksisterer
        if(dbUser.getUserById(userName) != null){
            return -1;
        }

        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password.toCharArray(), salt);

        String saltString = DatatypeConverter.printHexBinary(salt);
        String hashedPasswordString = DatatypeConverter.printHexBinary(hashedPassword);
        /*
        String hashedPasswordString = new String(hashedPassword);
        String saltString = new String(salt);
       */
        user.setPassword(hashedPasswordString+saltString);

        dbUser.createUser(user);

        // TODO: Endre returtype?
        return 1;
    }

    public static void main(String[] args){
        CreateLogin c = new CreateLogin();
        User u = new User();
        u.setUserId("canders");
        u.setPassword("Anders123");
        int s = c.signUp(u);

        System.out.println(s);
    }

}
