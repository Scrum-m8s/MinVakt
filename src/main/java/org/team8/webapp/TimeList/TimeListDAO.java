package org.team8.webapp.TimeList;
/**
 *
 * @author Mr.Easter
 */
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.ShiftList.ShiftList;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class TimeListDAO extends DatabaseManagement{

    public TimeListDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<TimeList> getTimeLists(){
        ArrayList<TimeList> out = new ArrayList<>();
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

    public ArrayList<TimeList> getTimeListsById(String id){
        ArrayList<TimeList> out = new ArrayList<TimeList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Time_list WHERE user_id=?;");
                prep.setString(1, id);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting timelist by id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                sqle.printStackTrace();
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public TimeList getSingleTimeList(String id, int year, int month){
        TimeList out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Time_list WHERE user_id=? AND year=? AND month=?;");
                prep.setString(1, id);
                prep.setInt(2,year);
                prep.setInt(3, month);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting single timelist by id and month.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    //Updates time_list deviances with input data. Creates a new entry if row does not exist in a given year and month.
    //Designed to be called once a month.
    public boolean updateDeviance(ShiftList s_l, int year, int month){
        //Checks if a row exists given user_id and month.
        TimeList existingTimelist = new TimeList();
        boolean exists = rowExists(s_l.getUser_id(), year, month);
        if (exists){existingTimelist = getSingleTimeList(s_l.getUser_id(), year, month);}

        int numb = 0;

        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                if (exists){
                    //If a unique row exists and deviance<0, update absence.
                    if (s_l.getDeviance()<0) {
                        prep = conn.prepareStatement("UPDATE Time_list SET ordinary=?, absence=? WHERE user_id=? AND year=? AND month=?");
                        prep.setInt(1, existingTimelist.getOrdinary());           //Hard-coded for 8-hour shifts.
                        prep.setInt(2, existingTimelist.getAbsence() + s_l.getDeviance());
                        prep.setString(3, s_l.getUser_id());
                        prep.setInt(4,year);
                        prep.setInt(5, month);
                    }
                    //If a unique row exists and deviance>0, update overtime.
                    else{
                        prep = conn.prepareStatement("UPDATE Time_list SET ordinary=?, overtime=? WHERE user_id=? AND year=? AND month=?");
                        prep.setInt(1, existingTimelist.getOrdinary()+8);           //Hard-coded for 8-hour shifts.
                        prep.setInt(2,existingTimelist.getOvertime()+s_l.getDeviance());
                        prep.setString(3,s_l.getUser_id());
                        prep.setInt(4, year);
                        prep.setInt(5, month);

                    }
                }
                //If a unique row does not exist, create and fill in.
                else {
                    prep = conn.prepareStatement("INSERT INTO Time_list (user_id, year, month, ordinary, overtime, absence) VALUES (?, ?, ?, ?, ?, ?);");
                    prep.setString(1, s_l.getUser_id());
                    prep.setInt(2,year);
                    prep.setInt(3, month);
                    prep.setInt(4, 8);                                              //Hard-coded for 8-hour shifts.
                    if (s_l.getDeviance()>0){
                        prep.setInt(5,s_l.getDeviance());
                        prep.setInt(6,0);
                    }
                    else{
                        prep.setInt(5,0);
                        prep.setInt(6,s_l.getDeviance());
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

    public boolean rowExists(String id, int year, int month){
        boolean out=false;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("SELECT EXISTS(SELECT 1 FROM Time_list WHERE user_id=? AND year=? AND month=?)");
                prep.setString(1, id);
                prep.setInt(2, year);
                prep.setInt(3, month);
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
                prep = conn.prepareStatement("INSERT INTO Time_list (user_id, year, month, ordinary, overtime, absence) VALUES (?, ?, ?, ?, ?, ?);");
                prep.setString(1, e.getUser_id());
                prep.setInt(2, e.getYear());
                prep.setInt(3, e.getMonth());
                prep.setInt(4, e.getOrdinary());
                prep.setInt(5, e.getOvertime());
                prep.setInt(6, e.getAbsence());
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
                prep = conn.prepareStatement("UPDATE Time_list SET ordinary=?, overtime=?, absence=? WHERE user_id=? AND year=? AND month=?;");
                prep.setInt(1, e.getOrdinary());
                prep.setInt(2, e.getOvertime());
                prep.setInt(3, e.getAbsence());
                prep.setString(4, e.getUser_id());
                prep.setInt(5, e.getYear());
                prep.setInt(6, e.getMonth());
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

    public boolean removeTimeList(String id, int year, int month) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Time_list WHERE user_id=? AND year=? AND month=?;");
                prep.setString(1, id);
                prep.setInt(2, year);
                prep.setInt(3, month);
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
        s.setUser_id(res.getString("user_id"));
        s.setYear(res.getInt("year"));
        s.setMonth(res.getInt("month"));
        s.setOrdinary(res.getInt("ordinary"));
        s.setOvertime(res.getInt("overtime"));
        s.setAbsence(res.getInt("absence"));
        return s;
    }
}
