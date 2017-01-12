package org.team8.webapp.User;

/**
 *
 * @author Mr.Easter
 */
import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DatabaseManagement {

    public UserDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

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
                System.err.println("Issue with getting users.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

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
                System.err.println("Issue with getting user by id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createUser(User u) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO User (user_id, password) VALUES (?, ?);");
                prep.setString(1, u.getUserId());
                prep.setString(2, u.getPassword());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating user.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateUser(User u) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE User SET password=? WHERE user_id=?;");
                prep.setString(1, u.getPassword());
                prep.setString(2, u.getUserId());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating user.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

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
                System.err.println("Issue with removing user.");
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
        //res.getString("user_id"), res.getString("password")
        User u = new User();

        u.setUserId(res.getString("user_id"));
        u.setPassword(res.getString("password"));

        return u;
    }
}
