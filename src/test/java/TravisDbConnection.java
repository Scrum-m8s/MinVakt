
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Magnusfn on 10.01.2017.
 */
public class TravisDbConnection {
    private final String username = "travis";
    private final String databasedriver = "com.mysql.cj.jdbc.Driver";
    private final String databasename = "jdbc:mysql://127.0.0.1/" + username +
            "?user=" + username;
    private Connection connection = null;


    public TravisDbConnection() {
        try {
            Class.forName(databasedriver);
            connection = DriverManager.getConnection(databasename);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Issue with database driver.");
        } catch (SQLException SQLe) {
            System.err.println("Issue with connecting to database.");
        }
    }

    /**
     * Gets the current connection
     * @return The current database connection
     */
    public Connection getConnection(){
        return connection;
    }
}
