package org.team8.webapp.Shift;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 11.01.2017.
 * Edited by Mr_Easter on 12.01.2017 and 18.01.2017.
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
                prep = conn.prepareStatement("SELECT * FROM Shift;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting shifts. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
                prep = conn.prepareStatement("SELECT * FROM Shift WHERE Shift_id =?;");
                prep.setString(1, id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting shift by id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createShift(Shift e) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Shift (shift_id, hours, start_time, end_time) VALUES (?, ?, ?, ?);");
                prep.setInt(1, e.getShiftId());
                prep.setInt(2, e.getHours());
                prep.setInt(3, e.getStartTime());
                prep.setInt(4, e.getEndTime());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating shift. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateShift(Shift e) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Shift SET hours=?, start_time=?, end_time=? WHERE shift_id=?;");
                prep.setInt(1, e.getHours());
                prep.setInt(2, e.getStartTime());
                prep.setInt(3, e.getEndTime());
                prep.setInt(4, e.getShiftId());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating shift. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean removeShift(String id) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Shift WHERE shift_id=?;");
                prep.setString(1, id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing shift. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
        return s;
    }
}
