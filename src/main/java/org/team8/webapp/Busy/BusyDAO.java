package org.team8.webapp.Busy;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Created by Nina on 12.01.2017.
 * Edited by Mr_Easter on 12.01.2017 and 18.01.2017.
 */
public class BusyDAO extends DatabaseManagement {

    public BusyDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;


    /**
     * Retrieves all information from Busy table in the database
     * @return Arraylist with objects that contains every element in Busy table
     */
    public ArrayList<Busy> getBusy(){
        ArrayList<Busy> out = new ArrayList<Busy>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting busy. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }


    /**
     * Retrieves information for a specific id from Busy table in the database
     * @param id Id of the user
     * @return Arraylist with objects that contains every element for the specific id in Busy table
     */
    public ArrayList<Busy> getBusyById(String id){
        ArrayList<Busy> out = new ArrayList<Busy>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy WHERE user_id=?;");
                prep.setString(1, id);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting busy by id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves information for a single Busy element
     * @param my_date Date of busy element
     * @param shift_id Shift id of busy element
     * @param user_id User id of busy element
     * @return Object that contains information for the sigle element in the Busy table
     */
    public Busy getSingleBusy(Date my_date, int shift_id, String user_id){
        Busy out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy WHERE user_id=? AND shift_id=? AND my_date=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                prep.setDate(3, my_date);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting single busy by user_id and shift_id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Creates new busy element in database
     * @param b Busy object
     * @return Boolean if object was created successfully
     */
    public boolean createBusy(Busy b) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Busy (user_id, shift_id, my_date) VALUES (?, ?, ?);");
                prep.setString(1, b.getUser_id());
                prep.setInt(2, b.getShift_id());
                prep.setDate(3, b.getMy_date());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating busy. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
     * Updates a busy element in database
     * @param busies Old and new busy object that's going to be updated
     * @return Boolean if object was updated successfully
     */
    public boolean updateBusy(ArrayList<Busy> busies) {
        int numb = 0;
        Busy oldBusy = busies.get(0);
        Busy newBusy = busies.get(1);
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Busy SET my_date=?, user_id=?, shift_id=? WHERE my_date=? AND user_id=? AND shift_id=?;");
                prep.setDate(1, newBusy.getMy_date());
                prep.setString(2, newBusy.getUser_id());
                prep.setInt(3, newBusy.getShift_id());
                prep.setDate(4, oldBusy.getMy_date());
                prep.setString(5, oldBusy.getUser_id());
                prep.setInt(6, oldBusy.getShift_id());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating busy. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
     * Removes a busy element in database
     * @param my_date Date of busy element
     * @param shift_id Shift id of busy element
     * @param user_id User id of busy element
     * @return Boolean if object was removed successfully
     */
    public boolean removeBusy(Date my_date, int shift_id, String user_id){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Busy WHERE user_id = ? AND shift_id = ? AND my_date=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                prep.setDate(3, my_date);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing busy. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected Busy processRow(ResultSet res) throws SQLException {
        Busy b = new Busy();
        b.setUser_id(res.getString("user_id"));
        b.setShift_id(res.getInt("shift_id"));
        b.setMy_date(res.getDate("my_date"));
        return b;
    }
}