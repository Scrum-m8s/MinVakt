package org.team8.webapp.LoginManagment;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by espen on 24.01.2017.
 */
public class GenTemporaryPassword {

    public GenTemporaryPassword(){}


    public static String generateTemporaryPassword(){
        // hentet fra http://www.java2s.com/Code/Java/Security/GeneratearandomStringsuitableforuseasatemporarypassword.htm
        Random RANDOM = new SecureRandom();
        final int PASSWORD_LENGTH = 8;

        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

        String pw = "";
        for (int i=0; i<PASSWORD_LENGTH; i++)
        {
            int index = (int)(RANDOM.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }

        return pw;
    }
}
