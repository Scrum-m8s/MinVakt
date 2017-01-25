package org.team8.webapp.ShiftSwitch;

import javafx.scene.chart.PieChart;
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.Shift.Shift;
import org.team8.webapp.User.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Mr.Easter on 24/01/2017.
 */
public class ShiftSwitchDAO extends DatabaseManagement{
    public ShiftSwitchDAO(){
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<ShiftSwitch> getShiftSwitches() {
        ArrayList<ShiftSwitch> out = new ArrayList<ShiftSwitch>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_switch;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting shift switches. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public ShiftSwitch getSingleShiftSwitch(Date my_date, int shift_id, String user_id){
        ShiftSwitch out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Shift_switch WHERE my_date=? AND shift_id=? AND user_id = ?;");
                prep.setDate(1, my_date);
                prep.setInt(2, shift_id);
                prep.setString(3, user_id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting single shift switch. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createShiftSwitch(ShiftSwitch e) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Shift_switch (my_date, shift_id, user_id_from, user_id_to) VALUES (?, ?, ?, ?);");
                prep.setDate(1, e.getMy_date());
                prep.setInt(2, e.getShift_id());
                prep.setString(3, e.getUser_id_from());
                prep.setString(4, e.getUser_id_to());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating user. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
/*
    public boolean updateShiftSwitch(User e) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE User SET password=?, role = ? WHERE user_id=?;");
                prep.setString(1, e.getPassword());
                prep.setInt(2, e.getRole());
                prep.setString(3, e.getUser_id());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating user. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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

    public boolean removeShiftSwitch(Date my_date, int shift_id, String user_id_from) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM User WHERE my_date=? AND shift_id=? AND user_id_from=?;");
                prep.setDate(1,my_date);
                prep.setInt(2, shift_id);
                prep.setString(3, user_id_from);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing shift switch. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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

    protected ShiftSwitch processRow(ResultSet res) throws SQLException {
        ShiftSwitch e = new ShiftSwitch();
        e.setMy_date(res.getDate("my_date"));
        e.setShift_id(res.getInt("shift_id"));
        e.setUser_id_from(res.getString("user_id_from"));
        e.setUser_id_to(res.getString("user_id_to"));
        return e;
    }
}