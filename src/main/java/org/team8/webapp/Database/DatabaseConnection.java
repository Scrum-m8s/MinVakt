package org.team8.webapp.Database;

import org.team8.webapp.util.TravisUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Magnusfn on 10.01.2017.
 */
public class DatabaseConnection {
    private final String databasedriver = "com.mysql.cj.jdbc.Driver";
    private final String username = TravisUtil.isTravis() ? "root" : "g_scrum08";
    private final String password = TravisUtil.isTravis() ? "" : "zAh9W2AO";
    private final String databasename = TravisUtil.isTravis() ? "jdbc:mysql://localhost/test" : "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/g_scrum08";

    private Connection connection = null;


    public DatabaseConnection() {
        try {
            Class.forName(databasedriver);
            connection = DriverManager.getConnection(databasename,username,password);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Issue with database driver.");
            cnfe.printStackTrace();
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
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
