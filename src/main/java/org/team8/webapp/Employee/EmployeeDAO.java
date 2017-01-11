package org.team8.webapp.Employee;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 */
public class EmployeeDAO extends DatabaseManagement {

    public EmployeeDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> out = new ArrayList<Employee>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Employee;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting employees.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public Employee getEmployeeById(String id){
        Employee out = null;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Employee WHERE user_id = ?;");
                prep.setString(1, id);
                res = prep.executeQuery();
                if (res.next()){
                    out = processRow(res);
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting employee by id.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    public boolean createEmployee(Employee e) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Employee (user_id, firstname, surname, email, phone_number, category) VALUES (?, ?, ?, ?, ?, ?);");
                prep.setString(1, e.getUserId());
                prep.setString(2, e.getFirstname());
                prep.setString(3, e.getSurname());
                prep.setString(4, e.getEmail());
                prep.setString(5, e.getPhoneNumber());
                prep.setInt(6, e.getCategory());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating employee.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean updateEmployee(Employee e) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("UPDATE Employee SET firstname=?, surname=?, email=?, phone_number=?, category=? WHERE user_id=?;");
                prep.setString(1, e.getFirstname());
                prep.setString(2, e.getSurname());
                prep.setString(3, e.getEmail());
                prep.setString(4, e.getPhoneNumber());
                prep.setInt(5, e.getCategory());
                prep.setString(6, e.getUserId());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating employee.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    public boolean removeEmployee(String id) {
        int numb = 0;
        if(setUp()) {
            try {
                conn = getConnection();
                conn.setAutoCommit(false);
                prep = conn.prepareStatement("DELETE FROM Employee WHERE user_id=?;");
                prep.setString(1, id);
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with removing employee.");
                rollbackStatement();
                return false;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return numb > 0;
    }

    protected Employee processRow(ResultSet res) throws SQLException {
        Employee e = new Employee();
        e.setUserId(res.getString("user_id"));
        e.setFirstname(res.getString("firstname"));
        e.setSurname(res.getString("surname"));
        e.setEmail(res.getString("email"));
        e.setPhoneNumber(res.getString("phone_number"));
        e.setCategory(res.getInt("category"));
        return e;
    }
}