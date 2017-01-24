package org.team8.webapp.ShiftList;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
* Created by mariyashchekanenko on 12/01/2017.
* Edited by Mr_Easter on 12/01/2017 and 18.01.2017.
*/

public class ShiftListDAO extends DatabaseManagement{

    public ShiftListDAO () {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<ShiftList> getShiftLists(){
        ArrayList<ShiftList> out = new ArrayList<ShiftList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting shiftlists. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public ArrayList<ShiftList> getShiftListsById(String user_id){
        ArrayList<ShiftList> out = new ArrayList<ShiftList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE user_id=?;");
                prep.setString(1, user_id);
                res = prep.executeQuery();
                while(res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting Shift_list by user_id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public ArrayList<ShiftList> getShiftListsByDate(String my_date){
        ArrayList<ShiftList> out = new ArrayList<ShiftList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE my_date=?;");
                prep.setString(1, my_date);
                res = prep.executeQuery();
                while(res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting Shift_list by date. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    //TODO: må finne en smartere løsning på å finne enkelt shift
    public ShiftList getSingleShift(String user_id, int shift_id){
        ShiftList out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE user_id=? AND shift_id=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting single shift_list by user id and shift_id. Error code:" + sqle.getErrorCode() + " Message: " + sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }


    public ArrayList<ShiftList> getWantSwap(boolean swap){
        ArrayList<ShiftList> out = new ArrayList<ShiftList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE want_swap=?;");
                prep.setBoolean(1, swap);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting swapList.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createShiftlist(ShiftList s_l){
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Shift_list (user_id, shift_id, on_duty, my_date, deviance, want_swap) VALUES (?, ?, ?, ?, ?, ?);");
                prep.setString(1, s_l.getUser_id());
                prep.setInt(2, s_l.getShift_id());
                prep.setBoolean(3, s_l.isOn_duty());
                prep.setDate(4, s_l.getMy_date());
                prep.setInt(5, s_l.getDeviance());
                prep.setBoolean(6, s_l.isWant_swap());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating shiftlist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateShiftlist(ShiftList s_l){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET shift_id=?, on_duty=?, my_date=?, deviance=?, want_swap=? WHERE user_id=? AND shift_id=? AND my_date=?;");
                prep.setInt(1, s_l.getShift_id());
                prep.setBoolean(2, s_l.isOn_duty());
                prep.setDate(3, s_l.getMy_date());
                prep.setInt(4, s_l.getDeviance());
                prep.setBoolean(5, s_l.isWant_swap());
                prep.setString(6, s_l.getUser_id());
                prep.setInt(7, s_l.getShift_id());
                prep.setDate(8, s_l.getMy_date());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating shiftlist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                sqle.printStackTrace();
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }
    public boolean registerDeviance(ShiftList s_l) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET deviance=? WHERE  user_id=? AND shift_id=? AND my_date=?;");
                prep.setInt(1, s_l.getDeviance());
                prep.setString(2, s_l.getUser_id());
                prep.setInt(3, s_l.getShift_id());
                prep.setDate(4, s_l.getMy_date());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating deviance.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }


    public boolean removeShiftlist(String user_id, int shift_id){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Shift_list WHERE user_id = ? AND shift_id = ?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing shiftlist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    //Removes deviance from shift_list give user_id and shift_id.
    //TODO: Can this be replaced by previous update-function?
    public boolean removeDeviance(String user_id, int shift_id){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET deviance=? WHERE user_id = ? AND shift_id = ?;");
                prep.setInt(1,0);
                prep.setString(2, user_id);
                prep.setInt(3, shift_id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing shiftlist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected ShiftList processRow(ResultSet res) throws SQLException {
        ShiftList s_l = new ShiftList();
        s_l.setUser_id(res.getString("user_id"));
        s_l.setShift_id(res.getInt("shift_id"));
        s_l.setOn_duty(res.getBoolean("on_duty"));
        s_l.setMy_date(res.getDate("my_date"));
        s_l.setDeviance(res.getInt("deviance"));
        s_l.setWant_swap(res.getBoolean("want_swap"));
        return s_l;
    }
}