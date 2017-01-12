package org.team8.webapp.LoginManagment;

import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import javax.xml.bind.DatatypeConverter;

import static org.team8.webapp.LoginManagment.Hash.generateSalt;
import static org.team8.webapp.LoginManagment.Hash.hashPassword;

/**
 * Created by espen on 11.01.2017.
 */

public class CreateLogin {

    public UserDAO dbUser = new UserDAO();

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
