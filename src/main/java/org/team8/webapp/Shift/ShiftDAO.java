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
                prep = conn.prepareStatement("SELECT * FROM Shift WHERE shift_id = ? ");
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

    //TODO: Add create, update, remove. Look at EmployeeDAO for guidelines.

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
