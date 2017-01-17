import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.team8.webapp.Database.DatabaseConnection;

/**
 * Created by KristofferLaptop on 16-Jan-17.
 */
public class DbConnectionTest {


    @Mock
    DatabaseConnection connection = new DatabaseConnection();

    @Test
    public void testConnection(){
        Assert.assertNotNull(connection.getConnection());
    }
}

