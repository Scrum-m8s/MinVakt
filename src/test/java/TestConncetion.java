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

    @InjectMocks
    TravisDbConnection connection = new TravisDbConnection();

    @Test
    void testConnection(){
        Assert.assertNotNull(connection.getConnection());
    }
}
