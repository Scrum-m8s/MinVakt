import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by KristofferLaptop on 12-Jan-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestConncetion {

    @Mock
    TravisDbConnection connection = new TravisDbConnection();

    @Test
    public void testConnection(){
        Assert.assertNotNull(connection.getConnection());
    }
}
