package org.team8.webapp.LoginManagment;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by espen on 10.01.2017.
 */
public class Hash {

    // Juster etter behov
    private static final int SALT_LENGTH = 32;
    private static final int NUM_OF_ITERATIONS = 10;
    private static final int KEY_LENGTH = 256;

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
    // Generert hash er 64 byte
    public static byte[] hashPassword( final char[] password, final byte[] salt) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, NUM_OF_ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;

        } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }

    // returnerer hash + salt som hexadesimal string med lengde på 128
    // første 64 er hash, siste 64 er salt
    public static String createHashedPassword(String password){

        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password.toCharArray(), salt);

        String saltString = DatatypeConverter.printHexBinary(salt);
        String hashedPasswordString = DatatypeConverter.printHexBinary(hashedPassword);

        return hashedPasswordString + saltString;
    }


    public static void main(String[] args){
        String password = "123456";
        char[] pword = password.toCharArray();

        byte[] salt = generateSalt();

        System.out.println(salt.length);

        byte[] hashet = hashPassword(pword, salt);

        for(int i=0;i<hashet.length;i++){
            System.out.println(hashet[i]);
        }

    }
}
