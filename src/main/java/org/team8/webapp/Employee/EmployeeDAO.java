package org.team8.webapp.Employee;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by asdfLaptop on 10.01.2017.
 * Edited by MisterEaster on 19.01.2017.
 */
@SuppressWarnings("ALL")
public class EmployeeDAO extends DatabaseManagement {

    public EmployeeDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    /**
     * Retrieves all information from employee table in the database
     * @return Arraylist with objects that contains every element in employee table
     */
    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> out = new ArrayList<>();
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
                System.err.println("Issue with getting employees. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves information for a specific id from employee table in the database
     * @param id Id of the employee
     * @return Object that contains every element for the specific id in employee table
     */
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
                System.err.println("Issue with getting employee by id. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves all employees that has a specific category in the employee table in the database
     * @param category Category of the employee
     * @return Arraylist with objects that contains every element for the specific category in employee table
     */
    public ArrayList<Employee> getEmployeeByCategory(int category){
        ArrayList<Employee> out = new ArrayList<>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Employee WHERE category =?;");
                prep.setInt(1, category);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting employee by category.");
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Retrieves all available employees for a specific shift from employee table in the database
     * @param shift_id Id of shift
     * @param my_date Date of shift
     * @param category Category of the employee
     * @return Arraylist with objects that contains every element for available employees from the employee table
     */
    public ArrayList<Employee> getAvailableEmployees(int shift_id, Date my_date, int category){
        ArrayList<Employee> out = new ArrayList<>();
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement(
                        "SELECT * FROM Employee WHERE user_id NOT IN " +
                            "(SELECT user_id FROM  `Busy` WHERE my_date =? AND shift_id =? " +
                            "UNION " +
                            "SELECT user_id FROM  `Shift_list` WHERE my_date =? AND shift_id =?) " +
                        "AND category =?;");
                prep.setDate(1, my_date);
                prep.setInt(2,shift_id);
                prep.setDate(3,my_date);
                prep.setInt(4,shift_id);
                prep.setInt(5,category);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException sqle){
                System.err.println("Issue with getting available employees. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }
        return out;
    }

    /**
     * Creates new employee element in database
     * @param e Employee object
     * @return Boolean if object was created successfully
     */
    public boolean createEmployee(Employee e) {
        int numb = 0;
        if(setUp()){
            try {
                conn = getConnection();
                prep = conn.prepareStatement("INSERT INTO Employee (user_id, firstname, surname, email, phone_number, category) VALUES (?, ?, ?, ?, ?, ?);");
                prep.setString(1, e.getUser_id());
                prep.setString(2, e.getFirstname());
                prep.setString(3, e.getSurname());
                prep.setString(4, e.getEmail());
                prep.setString(5, e.getPhone_number());
                prep.setInt(6, e.getCategory());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with creating employee. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
     * Updates a employee element in database
     * @param e Employee object with updated values
     * @return Boolean if object was updated successfully
     */
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
                prep.setString(4, e.getPhone_number());
                prep.setInt(5, e.getCategory());
                prep.setString(6, e.getUser_id());
                numb = prep.executeUpdate();
            }
            catch (SQLException sqle) {
                System.err.println("Issue with updating employee. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
     * Removes a employee element in database
     * @param id User id of employee element
     * @return Boolean if object was removed successfully
     */
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
                System.err.println("Issue with removing employee. Error code:" + sqle.getErrorCode() + " Message: " +sqle.getMessage());
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
     * Retrieves employees for a specific date from employee table in the database
     * @param date Date that employee is available
     * @return Arraylist with objects that contains every element for the specific date in employee table
     */
    public ArrayList<Employee> getEmployeesForDate(String date){
        ArrayList<Employee> out = new ArrayList<>();
        if(setUp()){
            try{
                conn = getConnection();
                prep = conn.prepareStatement("SELECT DISTINCT b.* FROM Shift_list AS a NATURAL JOIN Employee AS b WHERE a.my_date = ?");
                prep.setString(1, date);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }

            }catch (SQLException sqle) {
                System.err.println("Issue with getting employees for shift.");
                rollbackStatement();
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }

        return out;
    }

    /**
     * Retrieves employees for a specific shift from employee table in the database
     * @param shift_id Id of shift
     * @param date Date that employee is available
     * @return Arraylist with objects that contains every element for the specific shift in employee table
     */
    public ArrayList<Employee> getEmployeesForShift(int shift_id, String date){
        ArrayList<Employee> out = new ArrayList<>();
        if(setUp()){
            try{
                conn = getConnection();
                prep = conn.prepareStatement("SELECT b.* FROM Shift_list AS a NATURAL JOIN Employee AS b   WHERE a.shift_id = ? AND a.my_date = ?");
                prep.setInt(1, shift_id);
                prep.setString(2, date);
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }

            }catch (SQLException sqle) {
                System.err.println("Issue with getting employees for shift.");
                rollbackStatement();
                return null;
            }
            finally {
                finallyStatement(res, prep);
            }
        }

        return out;
    }


    protected Employee processRow(ResultSet res) throws SQLException {
        Employee e = new Employee();

        e.setUser_id(res.getString("user_id"));
        e.setFirstname(res.getString("firstname"));
        e.setSurname(res.getString("surname"));
        e.setEmail(res.getString("email"));
        e.setPhone_number(res.getString("phone_number"));
        e.setCategory(res.getInt("category"));

        return e;
    }
}
