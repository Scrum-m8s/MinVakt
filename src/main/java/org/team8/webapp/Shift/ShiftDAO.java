package org.team8.webapp.Shift;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 11.01.2017.
 */
public class ShiftDAO extends DatabaseManagement {

    public ShiftDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<Shift> getShifts(){
        ArrayList<Shift> out = new ArrayList<Shift>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift, Shift_list WHERE Shift.Shift_id = Shift_list.Shift_id;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting shifts.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public Shift getShiftById(String id){
        Shift out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift, Shift_list WHERE Shift.Shift_id = Shift_list.Shift_id AND user_id=?;");
                prep.setString(1, id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting shift by id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    //TODO: Test funksjonene create, update og remove shift opp mot en dummy database/junit
    public boolean createShift(Shift s) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Shift_list (user_id, shift_id, on_duty, my_date, deviance) VALUES (?, ?, ?, ?, ?);");
                prep.setString(1, s.getUserId());
                prep.setInt(2, s.getShiftId());
                prep.setBoolean(3, s.isOnDuty());
                prep.setDate(4, s.getMyDate());
                prep.setInt(5, s.getDeviance());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating shift.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateShift(Shift s) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift_list SET user_id=?, shift_id=?, on_duty=?, my_date=?, deviance=? WHERE user_id=?;");
                prep.setString(1, s.getUserId());
                prep.setInt(2, s.getShiftId());
                prep.setBoolean(3, s.isOnDuty());
                prep.setDate(4, s.getMyDate());
                prep.setInt(5, s.getDeviance());
                prep.setString(6, s.getUserId());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating shift.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean removeShift(String userId, int shiftId, Date date) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Shift_list WHERE user_id=? AND shift_id=? AND my_date=?;");
                prep.setString(1, userId);
                prep.setInt(2, shiftId);
                prep.setDate(3, date);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing shift.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected Shift processRow(ResultSet res) throws SQLException {
        Shift s = new Shift();
        s.setShiftId(res.getInt("shift_id"));
        s.setHours(res.getInt("hours"));
        s.setStartTime(res.getInt("start_time"));
        s.setEndTime(res.getInt("end_time"));
        s.setUserId(res.getString("user_id"));
        s.setOnDuty(res.getBoolean("on_duty"));
        s.setMyDate(res.getDate("my_date"));
        s.setDeviance(res.getInt("deviance"));
        return s;
    }
}
