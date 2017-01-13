package org.team8.webapp.Busy;
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.User.User;

import java.sql.*;
import java.util.ArrayList;
/**
 * Created by Nina on 12.01.2017.
 */
public class BusyDAO extends DatabaseManagement {

    public BusyDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<Busy> getAllBusy(){
        ArrayList<Busy> out = new ArrayList<Busy>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting busy.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public Busy getBusyByUserId(String user_id){
        Busy out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy WHERE user_id = ?;");
                prep.setString(1, user_id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting busy by id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean removeBusy(String user_id) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Busy WHERE user_id=?;");
                prep.setString(1, user_id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing busy.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public Busy getBusyByShiftId(int shiftID){
        Busy out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy WHERE Busy.shift_id = ?;");
                prep.setInt(1, shiftID);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting busy by shift id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public Busy getBusyByDate(Date my_date){
        Busy out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Busy WHERE my_date = ?;");
                prep.setDate(1, my_date);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting busy by date.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createBusy(Busy b) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Busy (user_id, shift_id, my_date) VALUES (?, ?, ?);");
                prep.setString(1, b.getUserId());
                prep.setInt(2, b.getShiftId());
                prep.setDate(3, b.getMyDate());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating busy.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected Busy processRow(ResultSet res) throws SQLException {
        Busy b = new Busy();
        b.setUserId(res.getString("user_id"));
        b.setShift_Id(res.getInt("shift_id"));
        b.setMyDate(res.getDate("my_date"));
        return b;
    }


}