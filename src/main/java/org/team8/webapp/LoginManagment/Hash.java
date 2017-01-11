package org.team8.webapp.LoginManagment;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by espen on 10.01.2017.
 */
public class Hash {

    private static final int SALT_LENGTH = 32;

    // Generer salt til hashfunsksjon, må lagres sammen med hashet passord
    public static byte[] generateSalt(){

        byte salt[] = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();

        random.nextBytes(salt);

        return salt;
    }

    // Hentet fra https://www.owasp.org/index.php/Hashing_Java
    // Antall iterasjoner burde være slik at det tar ca 1,5 sekund å hashe på server
    // keyLength burde være 256
    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;

        } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }

    public static void main(String[] args){
        String password = "123456";
        char[] pword = password.toCharArray();

        byte[] salt = generateSalt();

        System.out.println(salt.length);

        byte[] hashet = hashPassword(pword, salt, 10, 256);

        for(int i=0;i<hashet.length;i++){
            System.out.println(hashet[i]);
        }

    }
}
