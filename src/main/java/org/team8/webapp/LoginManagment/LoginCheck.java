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

    // Tar brukernavn og passord, skal returnere token hvis lykkes, ellers -1
    public static boolean validateCredentials(User user) {

        User databaseUser = dbUser.getUserById(user.getUserId());

        if (databaseUser == null) {
            return false;
        } else {

            String hashAndSalt = databaseUser.getPassword();
            byte[] hash = DatatypeConverter.parseHexBinary(hashAndSalt.substring(0, 64));
            byte[] salt = DatatypeConverter.parseHexBinary(hashAndSalt.substring(64));

            byte[] passwordHash = hashPassword(user.getPassword().toCharArray(), salt);

            return Arrays.equals(hash, passwordHash);

        }
    }

    public static void main(String[] args){
        User u = new User();
        u.setUserId("canders");
        u.setPassword("Anders123");
        boolean i = validateCredentials(u);
        System.out.println(i);
    }
}
