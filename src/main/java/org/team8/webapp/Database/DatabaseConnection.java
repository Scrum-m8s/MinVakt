package org.team8.webapp.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Magnusfn on 10.01.2017.
 */
public class DatabaseConnection {
    private final String username = "g_scrum08";
    private final String password = "zAh9W2AO";
    private final String databasedriver = "com.mysql.cj.jdbc.Driver";
    private final String databasename = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + username +
            "?user=" + username + "&password=" + password;
    private Connection connection = null;


    public DatabaseConnection() {
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
