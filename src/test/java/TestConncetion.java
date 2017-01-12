import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by KristofferLaptop on 12-Jan-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestConncetion {

    @Test
    void testConnection(){
        TravisDbConnection connection = new TravisDbConnection();
        Assert.assertNotNull(connection.getConnection());
    }
}
