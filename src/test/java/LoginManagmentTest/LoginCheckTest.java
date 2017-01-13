package LoginManagmentTest;

import org.junit.Test;
import org.team8.webapp.User.User;

import static org.junit.Assert.*;
import static org.team8.webapp.LoginManagment.LoginCheck.*;


/**
 * Created by espen on 11.01.2017.
 */
public class LoginCheckTest {
    @Test
    public void testValidateCredentials() throws Exception {
        User u = new User();

        // Finnes i databasen
        u.setUserId("canders");
        u.setPassword("Anders123");

        assertTrue(validateCredentials(u) > -1);

        // Feil passord
        u.setPassword("aksjbfhjjh");

        assertFalse(validateCredentials(u) > -1);

        // Finnes ikke i databasen
        u.setUserId("aslflbaglb");
        u.setPassword("Anders123");

        assertFalse(validateCredentials(u) > -1);

    }
}
