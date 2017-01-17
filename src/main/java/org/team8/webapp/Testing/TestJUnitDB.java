package org.team8.webapp.Testing;

import org.junit.*;
import org.team8.webapp.Busy.Busy;
import org.team8.webapp.Busy.BusyDAO;
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.Employee.Employee;
import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.Shift.Shift;
import org.team8.webapp.Shift.ShiftDAO;
import org.team8.webapp.TimeList.TimeListDAO;
import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestJUnitDB extends DatabaseManagement{
    private static EmployeeDAO employeeDAO;
    private static ShiftDAO shiftDAO;
    private static UserDAO userDAO;
    private static BusyDAO busyDAO;
    private static TimeListDAO timeListDAO;


    private String[] validUser = new String[2];
    private String[] invalidUser = new String[2];

    private final int NO_ACCESS = 0;
    private final int ACCESS = 1;

    /**
     *  Bare for testing
     */
    private boolean testExecuteSQL(String sql){
        if(setUp()){
            PreparedStatement prep = null;
            try {
                getConnection().setAutoCommit(false);
                prep = getConnection().prepareStatement(sql);
                prep.execute();
            }catch (SQLException e){
                e.printStackTrace();
                rollbackStatement();
                return false;
            }finally {
                finallyStatement(null, prep);
            }
            return true;
        }
        return false;
    }

    @BeforeClass
    public static void DBsetUp(){
        try {
            employeeDAO = new EmployeeDAO();
            shiftDAO = new ShiftDAO();
            userDAO = new UserDAO();
            busyDAO = new BusyDAO();

        }
        catch(Exception e) {
            System.err.println("Issue with database connections.");
            e.printStackTrace();
        }
    }


    //
    //User-tests
    //
    @Test
    public void getUsers(){
        assertNotNull(userDAO.getUsers());
    }

    @Test
    public void getUserById() {
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));

        //test fetching of data
        assertNotNull(userDAO.getUserById("dummy"));

        //clean up
        userDAO.removeUser("dummy");
    }

    @Test
    public void createUser() {
        try {
            assertTrue(userDAO.createUser(new User("dummy", "dummy", 1)));
        }
        catch (Exception e){
            System.err.println("Issue with database connection.");
            e.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            String sql = "DELETE FROM User WHERE user_id = 'dummy';";
            testExecuteSQL(sql);
        }
    }

    @Test
    public void updateUser() {
        //creating dummy user to update
        userDAO.createUser(new User("dummy", "dummy", 1));

        assertTrue(userDAO.updateUser(new User("dummy", "dummyUPDATED", 2)));

        //clean up
        userDAO.removeUser("dummy");
    }

    @Test
    public void removeUser() {
        //Create dummy user to delete
        User dummy = new User("dummy", "dummy", 1);
        userDAO.createUser(dummy);

        assertTrue(userDAO.removeUser(dummy.getUserId()));
    }


    //
    //Employee-tests
    //
    @Test
    public void getEmployees() {
        assertNotNull(employeeDAO.getEmployees());
    }

    @Test
    public void getEmployeeById() {
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));

        //creating dummy employee
        employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

        assertNotNull(employeeDAO.getEmployeeById("dummy"));

        //clean up
        employeeDAO.removeEmployee("dummy");
        userDAO.removeUser("dummy");
    }

    @Test
    public void createEmployee() {
        try {
            //creating dummy user to fetch
            userDAO.createUser(new User("dummy", "dummy", 1));
            assertTrue(employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1)));
        }
        catch (Exception ex){
            System.err.println("Issue with database connection.");
            ex.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            String sql1 = "DELETE FROM Employee WHERE user_id = 'dummy';";
            String sql2 = "DELETE FROM User WHERE user_id = 'dummy';";
            testExecuteSQL(sql1);
            testExecuteSQL(sql2);
        }
    }

    @Test
    public void updateEmployee() {
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));

        //creating dummy employee
        employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

        assertTrue(employeeDAO.updateEmployee(new Employee("dummy", "dummyUpdated", "dummyUpdated", "dummyUpdated", "dummyUpdated", 1)));

        //clean up
        employeeDAO.removeEmployee("dummy");
        userDAO.removeUser("dummy");
    }

    @Test
    public void removeEmployee() {
        //create dummy user to delete first
        userDAO.createUser(new User("dummy", "dummy", 1));

        //Create dummy employee to delete
        employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 3));

        //removing dummy data to avoid clutter in database
        assertTrue(employeeDAO.removeEmployee("dummy"));
        userDAO.removeUser("dummy");
    }


    //
    //Shift-tests
    //
    @Test
    public void getShifts(){
        assertNotNull(shiftDAO.getShifts());
    }

    @Test
    public void getShiftById() {
        assertNotNull(shiftDAO.getShiftById(3));
    }

    @Test
    public void createShift(){
        Shift dummy = new Shift(4, 1, 1, 1);

        try {
            assertTrue(shiftDAO.createShift(dummy));
        }
        catch (Exception ex){
            System.err.println("Issue with database connection.");
            ex.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            String sql = "DELETE FROM Shift WHERE shift_id = '" + dummy.getShiftId() + "';";
            testExecuteSQL(sql);
        }
    }
  
    @Test
    public void updateShift() {
        Shift dummy = new Shift(4, 1, 1, 1);
        shiftDAO.createShift(dummy);

        Shift updated = new Shift(4, 2, 2, 2);
        assertTrue(shiftDAO.updateShift(updated));

        shiftDAO.removeShift(updated.getShiftId());
    }

    @Test
    public void removeShift(){
        Shift dummy = new Shift(4, 1, 1, 1);
        shiftDAO.createShift(dummy);

        assertTrue(shiftDAO.removeShift(dummy.getShiftId()));
    }
  
  
    //
    //Busy-tests
    //
    @Test
    public void getBusy(){
        assertNotNull(busyDAO.getBusy());
    }

    @Test
    public void getBusyByUserIdAndShiftId() {
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));
        assertNotNull(busyDAO.getBusyByUserIdAndShiftId("dummy", 1));

        //clean up
        busyDAO.removeBusy("dummy", 1);
        userDAO.removeUser("dummy");
    }

    @Test
    public void createBusy() {
        String user = "dummy";
        int shift = 1;
        Date date = new Date(2017-11-12);

        //create dummy user to test
        userDAO.createUser(new User(user, "dummy", 1));

        Busy dummy = new Busy(user, shift, date);

        try {
            assertTrue(busyDAO.createBusy(dummy));
        }
        catch (Exception e){
            System.err.println("Issue with database connection.");
            e.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            String sql = "DELETE FROM Busy WHERE user_id = '" + user + "';";
        }
    }
  
    @Test
    public void updateBusy() {
        String username = "dummy";
        int shift = 1;
        Date date = new Date(2017-11-12);
        Date dateUpdated = new Date(2017-11-12);

        //create dummy user to test
        userDAO.createUser(new User(username, "dummy", 1));

        Busy updated = new Busy(username, shift, dateUpdated);

        assertTrue(busyDAO.updateBusy(updated));

        //Changes back to old values
        Busy old = new Busy(username, shift, date);
        busyDAO.updateBusy(old);
    }

    @Test
    public void removeBusy() {
        //Create dummy busy to delete
        String username = "dummy";
        int shift = 1;
        Date date = new Date(2017-11-12);

        Busy dummy = new Busy(username, shift, date);
        busyDAO.createBusy(dummy);

        assertTrue(busyDAO.removeBusy(dummy.getUserId(), dummy.getShiftId()));
    }
}