package org.team8.webapp.Shift_list;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
* Created by mariyashchekanenko on 12/01/2017.
* Edited by Mr_Easter on 12/01/2017.
*/

public class Shift_listDAO extends DatabaseManagement{

    public Shift_listDAO () {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<Shift_list> getShiftLists(){
        ArrayList<Shift_list> out = new ArrayList<Shift_list>();
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
                System.err.println("Issue with getting shiftlists.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public Shift_list getShiftListById(String user_id, int shift_id){
        Shift_list out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_list WHERE user_id =  AND shift_id = ?;");
                prep.setString(1, user_id);
                prep.setInt(2, shift_id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting Shift_list by user and shift id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createShiftlist(Shift_list s_l){
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
                System.err.println("Issue with creating shiftlist.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateShiftlist(Shift_list s_l){
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
                System.err.println("Issue with updating shiftlist.");
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
                System.err.println("Issue with removing shiftlist.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }
    protected Shift_list processRow(ResultSet res) throws SQLException {
        Shift_list s_l = new Shift_list();
        s_l.setUser_id(res.getString("user_id"));
        s_l.setShift_id(res.getInt("shift_id"));
        s_l.setOn_duty(res.getBoolean("on_duty"));
        s_l.setMy_date(res.getDate("my_date"));
        s_l.setDeviance(res.getInt("deviance"));
        return s_l;
    }
}
