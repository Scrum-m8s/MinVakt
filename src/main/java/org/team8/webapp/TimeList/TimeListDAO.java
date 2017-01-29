package org.team8.webapp.TimeList;
/**
 *
 * @author Mr.Easter
 */
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.ShiftList.ShiftList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("ALL")
public class TimeListDAO extends DatabaseManagement{

    public TimeListDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    /**
     * Retrieves all information from Time_list table in the database
     * @return Arraylist with objects that contains every element in Time_list table
     */
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

    /**
     * Retrieves information for a specific id from Time_list table in the database
     * @param id Id of the user
     * @return Arraylist with objects that contains every element for the specific id in Time_list table
     */
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
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }


    public ArrayList<TimeList> getTimeListsByMonth(int year, int month){
        ArrayList<TimeList> out = new ArrayList<TimeList>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Time_list WHERE year=? AND month=?;");
                prep.setInt(1, year);
                prep.setInt(2,month);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting timelists by month.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves information for a single time list element
     * @param year Year of time list element
     * @param month Month of time list element
     * @param id User id of time list element
     * @return Object that contains information for the sigle element in the time list table
     */
    public TimeList getSingleTimeList(int year, int month, String id){
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

    public boolean onShiftListCreate(ShiftList s_l) {
        boolean numb;
        Calendar cal = Calendar.getInstance();
        cal.setTime(s_l.getMy_date());

        //Checks if a row exists given user_id and month.
        TimeList existingTimelist = new TimeList();
        boolean exists = rowExists(s_l.getUser_id(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        //If exists, +8 ordinary to existing
        if (exists){
            existingTimelist = getSingleTimeList(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), s_l.getUser_id());
            existingTimelist.setOrdinary(existingTimelist.getOrdinary()+8);
            numb = updateTimeList(existingTimelist);
        }
        //If no timelist, create with 8 ordinary.
        else{numb=createTimeList(new TimeList(s_l.getUser_id(),cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),8,0,0));
        }
        return numb;
    }
    //TODO: Update so admin can approve, instead of automatically adding.
    public boolean onShiftListDevianceUpdate(ShiftList s_l) {
        int numb = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(s_l.getMy_date());

        TimeList existingTimelist = getSingleTimeList(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), s_l.getUser_id());

        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                //If deviance<0, update absence.
                if (s_l.getDeviance() < 0) {
                    prep = conn.prepareStatement("UPDATE Time_list SET absence=? WHERE user_id=? AND year=? AND month=?");
                    prep.setInt(1, existingTimelist.getAbsence() + s_l.getDeviance());
                    prep.setString(2, s_l.getUser_id());
                    prep.setInt(3, cal.get(Calendar.YEAR));
                    prep.setInt(4, cal.get(Calendar.MONTH));
                }
                //If deviance>0, update overtime.
                else {
                    prep = conn.prepareStatement("UPDATE Time_list SET overtime=? WHERE user_id=? AND year=? AND month=?");
                    prep.setInt(1, existingTimelist.getOvertime() + s_l.getDeviance());
                    prep.setString(2, s_l.getUser_id());
                    prep.setInt(3, cal.get(Calendar.YEAR));
                    prep.setInt(4, cal.get(Calendar.MONTH));

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

    public boolean onShiftListRemove(Date my_date,int shift_id,String user_id) {
        boolean numb;
        Calendar cal = Calendar.getInstance();
        cal.setTime(my_date);

        TimeList existingTimelist = getSingleTimeList(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), user_id);

        //If ordinary<=8, remove timelist
        if(existingTimelist.getOrdinary()<=8){
            numb = removeTimeList(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),user_id);
        }

        //Else -8 to ordinary
        else{
            existingTimelist.setOrdinary(existingTimelist.getOrdinary()-8);
            numb = updateTimeList(existingTimelist);
        }
        return numb;
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

    /**
     * Creates new time list element in database
     * @param e TimeList object
     * @return Boolean if object was created successfully
     */
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

    /**
     * Updates a time list element in database
     * @param e TimeList object
     * @return Boolean if object was updated successfully
     */
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

    /**
     * Removes a time list element in database
     * @param year Year of time list element
     * @param month Month of time list element
     * @param id Id of user in time list element
     * @return Boolean if object was removed successfully
     */
    public boolean removeTimeList(int year, int month, String id) {
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
