package org.team8.webapp.ShiftList;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.*;
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

    /**
     * Retrieves all information from Shift_list table in the database
     * @return Arraylist with objects that contains every element in Shift_list table
     */
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

    /**
     * Retrieves information for a specific user_id from shift_list table in the database
     * @param user_id Id of the user
     * @return Arraylist with objects that contains every element for the specific user_id in shift_list table
     */
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

    /**
     * Retrieves information for a specific date from shift_list table in the database
     * @param my_date Date of shift list
     * @return Arraylist with objects that contains every element for the specific date in shift_list table
     */
    public ArrayList<ShiftList> getShiftListsByDate(Date my_date){
        ArrayList<ShiftList> out = new ArrayList<ShiftList>();
        if(setUp()){
            try {

                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE my_date=?;");
                prep.setDate(1, my_date);
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

    public ArrayList<ShiftList> getShiftListsByDate1(String my_date){
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

    /**
     * Retrieves information for a specific id and date from shift_list table in the database
     * @param my_date Date for the shift list
     * @param shift_id Id of the shift
     * @return Arraylist with objects that contains every element for the specific shift id and date in shift_list table
     */
    public ArrayList<ShiftList> getShiftListsByDateAndShiftId(String my_date, int shift_id){
        ArrayList<ShiftList> out = new ArrayList<ShiftList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE my_date=? AND shift_id=?;");
                prep.setString(1, my_date);
                prep.setInt(2, shift_id);
                res = prep.executeQuery();
                while(res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting Shift_list by date and shift_id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves information for a single shift_list element
     * @param my_date date of busy element
     * @param shift_id shift id of busy element
     * @param user_id user id of busy element
     * @return Object that contains information for the sigle element in the shift_list table
     */
    public ShiftList getSingleShift(Date my_date, int shift_id, String user_id){
        ShiftList out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE user_id=? AND shift_id=? AND my_date=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                prep.setDate(3, my_date);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                sqle.printStackTrace();
                System.err.println("Issue with getting single shift_list by user id and shift_id. Error code:" + sqle.getErrorCode() + " Message: " + sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public ShiftList getSpesificShift(String my_date, int shift_id, String user_id){
        ShiftList out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE user_id=? AND shift_id=? AND my_date=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                prep.setString(3, my_date);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                sqle.printStackTrace();
                System.err.println("Issue with getting single shift_list by user id and shift_id. Error code:" + sqle.getErrorCode() + " Message: " + sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }


    public ArrayList<Shift_list_w_cat> getWantSwap_cat(){
        ArrayList<Shift_list_w_cat> out = new ArrayList<Shift_list_w_cat>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT a.category, b.* FROM Employee AS a NATURAL JOIN Shift_list AS b WHERE a.user_id = b.user_id AND b.want_swap = 1;");

                res = prep.executeQuery();
                while (res.next()){
                    Shift_list_w_cat s_l = new Shift_list_w_cat();
                    s_l.setUser_id(res.getString("user_id"));
                    s_l.setShift_id(res.getInt("shift_id"));
                    s_l.setOn_duty(res.getBoolean("on_duty"));
                    s_l.setMy_date(res.getDate("my_date"));
                    s_l.setCategory(res.getInt("category"));
                    out.add(s_l);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting swapList w/cat.");
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

    public boolean setWantSwap(String user_id, int shift_id, String my_date, boolean want_swap){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET want_swap=? WHERE user_id=? AND shift_id=? AND my_date=?;");
                prep.setBoolean(1, want_swap);
                prep.setString(2, user_id);
                prep.setInt(3, shift_id);
                prep.setString(4, my_date);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating shiftlist want_swap. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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

    public boolean updateShiftByUserId(String new_user_id, String user_id, int shift_id, Date my_date){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET user_id=?, want_swap=false WHERE shift_id=? AND my_date=? AND user_id=?;");
                prep.setString(1, new_user_id);
                prep.setInt(2, shift_id);
                prep.setDate(3, my_date);
                prep.setString(4, user_id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating shiftlist want_swap. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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

    /**
     * Creates new shift list element in database
     * @param s_l ShiftList object
     * @return Boolean if object was created successfully
     */
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

    /**
     * Updates a shiftlist element in database
     * @param s_l ShiftList object
     * @return Boolean if object was updated successfully
     */
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

    /**
     * Removes a shift list element in database
     * @param my_date Date of shift list element
     * @param shift_id Shift id of shift list element
     * @param user_id User id of shift list element
     * @return Boolean if object was removed successfully
     */
    public boolean removeShiftlist(Date my_date, int shift_id, String user_id){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Shift_list WHERE user_id = ? AND shift_id = ? AND my_date=?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                prep.setDate(3, my_date);
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

    //Removes deviance from shift_list given date, user_id and shift_id.
    public boolean removeDeviance(Date my_date, int shift_id, String user_id){
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET deviance=? WHERE user_id = ? AND shift_id = ? AND my_date=?;");
                prep.setInt(1,0);
                prep.setString(2, user_id);
                prep.setInt(3, shift_id);
                prep.setDate(4, my_date);
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
/*
    public boolean wantSwap(ShiftList s_l) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET want_swap=? WHERE user_id=? AND shift_id=? AND my_date;");
                prep.setBoolean(1, s_l.isWant_swap());
                prep.setString(2, s_l.getUser_id());
                prep.setInt(3, s_l.getShift_id());
                prep.setDate(4, s_l.setWant_swap());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating swap."+prep.toString());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }
    */
  
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