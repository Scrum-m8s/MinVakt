package LoginManagmentTest;

import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.team8.webapp.LoginManagment.Hash.createHashedPassword;
import static org.team8.webapp.LoginManagment.Hash.generateSalt;
import static org.team8.webapp.LoginManagment.Hash.hashPassword;


/**
 * Created by espen on 12.01.2017.
 */
public class Hashtest {

    @Test
    public void testCreateHashedPassword() throws Exception {

        String password = "arnold123!#¤%/æøå.";

        String hashSalt1 = createHashedPassword(password);
        String hashSalt2 = createHashedPassword(password);

        assertFalse(hashSalt1.equals(hashSalt2));

        String hash = hashSalt1.substring(0,64);
        String salt = hashSalt1.substring(64);

        byte[] saltBytes = DatatypeConverter.parseHexBinary(salt);
        byte[] hashBytes = DatatypeConverter.parseHexBinary(hash);

        byte[] repeatSalt = hashPassword(password.toCharArray(), saltBytes);

        assertArrayEquals(hashBytes, repeatSalt);

    }


}
