package org.team8.webapp.LoginManagment;

import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import javax.xml.bind.DatatypeConverter;

import java.util.Arrays;

import static org.team8.webapp.LoginManagment.Hash.hashPassword;

/**
 * Created by espen on 11.01.2017.
 */

public class LoginCheck {

    // Midlertidig til REST service er implementert
    public static UserDAO dbUser = new UserDAO();

    // Tar brukernavn og passord, skal returnere brukerrolle hvis lykkes, ellers -1
    public static int validateCredentials(User user) {

        User databaseUser = dbUser.getUserById(user.getUserId());

        if (databaseUser == null) {
            return -1;
        } else {

            String hashAndSalt = databaseUser.getPassword();
            byte[] hash = DatatypeConverter.parseHexBinary(hashAndSalt.substring(0, 64));
            byte[] salt = DatatypeConverter.parseHexBinary(hashAndSalt.substring(64));

            byte[] passwordHash = hashPassword(user.getPassword().toCharArray(), salt);

            if(Arrays.equals(hash, passwordHash)){
                return databaseUser.getRole();
            }else{
                return -1;
            }
        }
    }

    public static void main(String[] args){
        User u = new User("employee", "123");
        System.out.println(validateCredentials(u));
    }
}
