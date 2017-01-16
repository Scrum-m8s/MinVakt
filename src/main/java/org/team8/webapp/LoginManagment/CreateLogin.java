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

    public boolean signUp(User user){

        String userName = user.getUserId();
        String password = user.getPassword();

        // Hvis brukernavn allerede eksisterer
        if(dbUser.getUserById(userName) != null){
            return false;
        }

        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password.toCharArray(), salt);

        String saltString = DatatypeConverter.printHexBinary(salt);
        String hashedPasswordString = DatatypeConverter.printHexBinary(hashedPassword);

        user.setPassword(hashedPasswordString+saltString);

        return dbUser.createUser(user);

    }

    public static void main(String[] args){

    }

}
