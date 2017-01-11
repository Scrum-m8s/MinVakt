package org.chiaboy.webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 */
public class EmployeeDAO extends DatabaseManagement{

    public EmployeeDAO() {
        super();
    }

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> out = new ArrayList<>();
        if(setUp()){
            try{
                conn = getConnection();
                prep = conn.prepareStatement("SELECT * FROM Employee;");
                res = prep.executeQuery();
                while (res.next()){
                    out.add(processRow(res));
                }
            }
            catch (SQLException e){
                System.err.println("Issue with getting employees.");
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
        e.setUserId(res.getString("user_id"));
        e.setFirstname(res.getString("firstname"));
        e.setSurname(res.getString("surname"));
        e.setEmail(res.getString("email"));
        e.setPhoneNumber(res.getString("phone_number"));
        e.setCategory(res.getInt("category"));
        return e;
    }
}
