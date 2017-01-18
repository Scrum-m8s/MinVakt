package org.team8.webapp.ShiftList;

import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.TimeList.TimeList;
import org.team8.webapp.TimeList.TimeListDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
* Created by mariyashchekanenko on 12/01/2017.
* Edited by Mr_Easter on 12/01/2017.
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

    public ShiftList getShiftListById(String user_id, int shift_id){
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
                System.err.println("Issue with getting Shift_list by user and shift id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
                prep = conn.prepareStatement("INSERT INTO Shift_list (user_id, shift_id, on_duty, my_date, deviance) VALUES (?, ?, ?, ?, ?);");
                prep.setString(1, s_l.getUser_id());
                prep.setInt(2, s_l.getShift_id());
                prep.setBoolean(3, s_l.isOn_duty());
                prep.setDate(4, s_l.getMy_date());
                prep.setInt(5, s_l.getDeviance());
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
                prep = conn.prepareStatement("UPDATE Shift_list SET on_duty=?, my_date=?, deviance=? WHERE user_id=? AND shift_id=?;");
                prep.setBoolean(1, s_l.isOn_duty());
                prep.setDate(2, s_l.getMy_date());
                prep.setInt(3, s_l.getDeviance());
                prep.setString(4, s_l.getUser_id());
                prep.setInt(5, s_l.getShift_id());
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

    public boolean updateDeviance(ShiftList s_l, String month){
        int numb = 0;
        TimeListDAO dao = new TimeListDAO();
        
        if(setUp()) {
            //Oppdaterer time_list.
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                //Sjekker om en unik rad eksisterer.
                boolean exists = dao.rowExists(s_l.getUser_id(),month);
                if (exists){
                    TimeList existingTimelist = dao.getTimeListByIdAndMonth(s_l.getUser_id(), month);
                    //Hvis en unik rad eksisterer og deviance<0, oppdater frav�r.
                    if (s_l.getDeviance()<0) {
                        prep = conn.prepareStatement("UPDATE Time_list SET absence=? WHERE user_id=? AND month=?");
                        prep.setInt(1, existingTimelist.getAbsence() + s_l.getDeviance());
                        prep.setString(2, s_l.getUser_id());
                        prep.setString(3, month);
                    }
                    //Hvis en unik rad eksisterer og deviance>0, oppdater overtid.
                    else{
                        prep = conn.prepareStatement("UPDATE Time_list SET overtime=? WHERE user_id=? AND month=?");
                        prep.setInt(1,existingTimelist.getOvertime()+s_l.getDeviance());
                        prep.setString(2,s_l.getUser_id());
                        prep.setString(3,month);

                    }
                }
                //Hvis en unik rad ikke eksisterer, opprett og fyll inn.
                else {
                    prep = conn.prepareStatement("INSERT INTO Time_list (user_id, month, ordinary, overtime, absence) VALUES (?, ?, ?, ?, ?);");
                    prep.setString(1, s_l.getUser_id());
                    prep.setString(2, month);
                    prep.setInt(3, 0);
                    if (s_l.getDeviance()>0){
                        prep.setInt(4,s_l.getDeviance());
                        prep.setInt(5,0);
                    }
                    else{
                        prep.setInt(4,0);
                        prep.setInt(5,s_l.getDeviance());
                    }
                }
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating deviance. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }


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
    protected ShiftList processRow(ResultSet res) throws SQLException {
        ShiftList s_l = new ShiftList();
        s_l.setUser_id(res.getString("user_id"));
        s_l.setShift_id(res.getInt("shift_id"));
        s_l.setOn_duty(res.getBoolean("on_duty"));
        s_l.setMy_date(res.getDate("my_date"));
        s_l.setDeviance(res.getInt("deviance"));
        return s_l;
    }
}
