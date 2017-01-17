package org.team8.webapp.Database;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;

/**
 * Created by asdfLaptop on 10.01.2017.
 */
public abstract class DatabaseManagement {
    private Connection connection;
    private Statement scentence;
    private DatabaseConnection c;

    public DatabaseManagement(){}

    protected boolean connected(){
        return scentence != null;
    }

    protected boolean setUp(){
        try {
            c = new DatabaseConnection();
            connection = c.getConnection();
            scentence = connection.createStatement();
        } catch (Exception e) {
            System.err.println("Connecting to database failed.");
            closeConnection();
            return false;
        }
        return !(connection == null || scentence == null);
    }
    protected void closeConnection(){
        try {
            if(!scentence.isClosed() && scentence != null) DbUtils.closeQuietly(scentence);
            if(!connection.isClosed() && connection != null) DbUtils.closeQuietly(connection);
        }
        catch (Exception e){
            System.err.println("Problem with closing connection.");
        }
    }

    public void rollbackStatement() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException ee) {
            System.err.println("Rollback Statement failed");
        }
    }


    public void finallyStatement(ResultSet res, PreparedStatement prep) {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
                connection.setAutoCommit(true);
            }
            if (res != null &&!res.isClosed()) res.close();
            if (prep != null && !prep.isClosed()) prep.close();
        } catch (SQLException sqle) {
            System.err.println("Finally Statement failed");
            sqle.printStackTrace();
        }
        closeConnection();
    }

    protected Statement getScentence() {
        return scentence;
    }

    protected Connection getConnection() { return connection; }

}
