package org.team8.webapp.User;

/**
 *
 * @author Mr.Easter
 */
import org.team8.webapp.Database.DatabaseManagement;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class UserDAO extends DatabaseManagement {

    public UserDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    /**
     * Retrieves all information from User table in the database
     * @return Arraylist with objects that contains every element in User table
     */
    public ArrayList<User> getUsers(){
        ArrayList<User> out = new ArrayList<User>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM User;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting users. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves information for a single User element
     * @param id User id of user element
     * @return Object that contains information for the sigle element in the User table
     */
    public User getUserById(String id){
        User out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM User WHERE user_id = ?;");
                prep.setString(1, id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting user by id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Creates new user element in database
     * @param e User object
     * @return Boolean if object was created successfully
     */
    public boolean createUser(User e) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO User (user_id, password, role) VALUES (?, ?, ?);");
                prep.setString(1, e.getUser_id());
                prep.setString(2, e.getPassword());
                prep.setInt(3, e.getRole());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating user. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    /**
     * Updates a user element in database
     * @param e User object
     * @return Boolean if object was updated successfully
     */
    public boolean updateUser(User e) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE User SET password=?, role = ? WHERE user_id=?;");
                prep.setString(1, e.getPassword());
                prep.setInt(2, e.getRole());
                prep.setString(3, e.getUser_id());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating user. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    /**
     * Removes a user element in database
     * @param id User id
     * @return Boolean if object was removed successfully
     */
    public boolean removeUser(String id) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM User WHERE user_id=?;");
                prep.setString(1, id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing user. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected User processRow(ResultSet res) throws SQLException {
        User e = new User();
        e.setUser_id(res.getString("user_id"));
        e.setPassword(res.getString("password"));
        e.setRole(res.getInt("role"));
        return e;
    }
}
