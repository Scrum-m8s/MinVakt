package org.team8.webapp.TimeList;
/**
 *
 * @author Mr.Easter
 */
import org.team8.webapp.Database.DatabaseManagement;

import java.sql.*;
import java.util.ArrayList;

public class TimeListDAO extends DatabaseManagement{

    public TimeListDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<TimeList> getTimeLists(){
        ArrayList<TimeList> out = new ArrayList<TimeList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Time_list;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting timelists. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }
    
    public TimeList getTimeListByIdAndMonth(String id, String month){
        TimeList out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Time_list WHERE user_id=? AND month=?;");
                prep.setString(1, id);
                prep.setString(2, month);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting timelist by id and month. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean rowExists(String id, String month){
        boolean out=false;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("SELECT EXISTS(SELECT 1 FROM Time_list WHERE user_id=? AND month=?)");
                prep.setString(1, id);
                prep.setString(2, month);
                res = prep.executeQuery();
                if (res.next()){
                    out = res.getBoolean(1);
                }
            }
            catch (SQLException sqle) {
                System.err.println("Issue with finding row existence. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }
    
    public boolean createTimeList(TimeList e) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Time_list (user_id, month, ordinary, overtime, absence) VALUES (?, ?, ?, ?, ?);");
                prep.setString(1, e.getUserId());
                prep.setString(2, e.getMonth());
                prep.setInt(3, e.getOrdinary());
                prep.setInt(4, e.getOvertime());
                prep.setInt(5, e.getAbsence());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating timelist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateTimeList(TimeList e) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Time_list SET ordinary=?, overtime=?, absence=? WHERE shift_id=? AND month=?;");
                prep.setInt(1, e.getOrdinary());
                prep.setInt(2, e.getOvertime());
                prep.setInt(3, e.getAbsence());
                prep.setString(4, e.getUserId());
                prep.setString(5, e.getMonth());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating timelist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean removeTimeList(String id, String month) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Shift WHERE shift_id=? AND month=?;");
                prep.setString(1, id);
                prep.setString(2, month);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing timelist. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected TimeList processRow(ResultSet res) throws SQLException {
        TimeList s = new TimeList();
        s.setUserId(res.getString("user_id"));
        s.setMonth(res.getString("month"));
        s.setOrdinary(res.getInt("ordinary"));
        s.setOvertime(res.getInt("overtime"));
        s.setAbsence(res.getInt("absence"));
        return s;
    }
}
