package org.team8.webapp.Busy;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Busy getSingleBusy(String user_id, int shift_id){
        Busy out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy WHERE user_id=? AND shift_id=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
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

    public boolean removeBusy(String user_id, int shift_id, Date my_date){
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