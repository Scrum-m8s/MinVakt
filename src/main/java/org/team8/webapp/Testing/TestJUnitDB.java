package org.team8.webapp.Testing;

import org.junit.*;
import org.team8.webapp.Busy.BusyDAO;
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.Employee.Employee;
import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.Shift.Shift;
import org.team8.webapp.Shift.ShiftDAO;
import org.team8.webapp.ShiftList.ShiftList;
import org.team8.webapp.ShiftList.ShiftListDAO;
import org.team8.webapp.TimeList.TimeList;
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
    private static ShiftListDAO shiftListDAO;


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
            timeListDAO = new TimeListDAO();
            shiftListDAO = new ShiftListDAO();
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
        assertNotNull(userDAO.getUserById("haakonrp"));
    }

    @Test
    public void createUser() {

        String user = "dummy";
        String pass = "dummy";

        User u = new User(user, pass, 1);

        try {
            assertTrue(userDAO.createUser(u));
        }
        catch (Exception e){
            System.err.println("Issue with database connection.");
            e.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            String sql = "DELETE FROM User WHERE user_id = '" + user + "';";
            testExecuteSQL(sql);
        }
    }

    @Test
    public void updateUser() {
        User updated = new User("haakonrp", "updateTest", 1);

        assertTrue(userDAO.updateUser(updated));

        //Changes back to old values
        User old = new User("haakonrp", "Haakonrp123", 1);
        userDAO.updateUser(old);
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
        assertNotNull(employeeDAO.getEmployeeById("haakonrp"));
    }

    @Test
    public void createEmployee() {
        //creating dummy user first
        User dummy = new User("dummy", "dummy", 1);

        String firstname = "Ola";
        String surname = "Nordmann";
        String email = "email@test.com";
        String phone = "12345678";
        int category = 1;

        Employee e = new Employee(dummy.getUserId(), firstname, surname, email, phone, category);


        try {
            userDAO.createUser(dummy);
            assertTrue(employeeDAO.createEmployee(e));
        }
        catch (Exception ex){
            System.err.println("Issue with database connection.");
            ex.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            String sql1 = "DELETE FROM Employee WHERE user_id = '" + dummy.getUserId() + "';";
            String sql2 = "DELETE FROM User WHERE user_id = '" + dummy.getUserId() + "';";
            testExecuteSQL(sql1);
            testExecuteSQL(sql2);
        }
    }

    @Test
    public void updateEmployee() {
        String username = "haakonrp";
        String firstname = "Haakon";
        String surname = "Paulsen";
        String email = "email@email.com";
        String phone = "12345678";
        int category = 2;

        Employee updated = new Employee(username, "Ola", "Nordmann", "test@test.com", "87654321", 1);

        assertTrue(employeeDAO.updateEmployee(updated));

        //Changes back to old values
        Employee old = new Employee(username, firstname, surname, email, phone, category);
        employeeDAO.updateEmployee(old);
    }

    @Test
    public void removeEmployee() {
        //create dummy user to delete first
        User dummyUser = new User("dummy", "dummy", 1);
        userDAO.createUser(dummyUser);

        //Create dummy employee to delete
        Employee dummyEmployee = new Employee(dummyUser.getUserId(), "dummy", "dummy", "dummy", "dummy", 3);
        employeeDAO.createEmployee(dummyEmployee);

        //removing dummy data to avoid clutter in database
        assertTrue(employeeDAO.removeEmployee(dummyUser.getUserId()));
        userDAO.removeUser(dummyUser.getUserId());
    }


    //
    //shift-test
    //
    @Test
    public void getShifts(){
        assertNotNull(shiftDAO.getShifts());
    }

    @Test
    public void getShiftById() {
        //creating dummy-shift to fetch
        shiftDAO.createShift(new Shift(4,4,4,4));
        assertNotNull(shiftDAO.getShiftById(4));
        //deleting dummy-shift
        shiftDAO.removeShift(4);
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
        //creating dummy data to fetch
        Shift dummy = new Shift(4, 1, 1, 1);
        shiftDAO.createShift(dummy);

        Shift updated = new Shift(4, 2, 2, 2);
        assertTrue(shiftDAO.updateShift(updated));

        //clean up
        shiftDAO.removeShift(updated.getShiftId());
    }

    @Test
    public void removeShift(){
        //creating dummy data to fetch
        Shift dummy = new Shift(4, 1, 1, 1);
        shiftDAO.createShift(dummy);

        //clean up and test
        assertTrue(shiftDAO.removeShift(dummy.getShiftId()));
    }


    //
    //timelist-tests
    //
    @Test
    public void getTimeLists(){
        assertNotNull(timeListDAO.getTimeLists());
    }

    @Test
    public void getTimeListByIdAndMonth() {
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", "dummy", 60, 0, 0));

        assertNotNull(timeListDAO.getTimeListByIdAndMonth("dummy", "dummy"));

        //clean up
        timeListDAO.removeTimeList("dummy", "dummy");
        userDAO.removeUser("dummy");
    }

    @Test
    public void createTimelist(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        assertTrue(timeListDAO.createTimeList(new TimeList("dummy", "dummy", 60, 0, 0)));

        //clean up
        timeListDAO.removeTimeList("dummy", "dummy");
        userDAO.removeUser("dummy");
    }

    @Test
    public void updateTimeList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", "dummy2", 60, 0, 0));

        assertTrue(timeListDAO.updateTimeList(new TimeList("dummy2", "dummy2", 65, 1, 1)));

        //clean up
        timeListDAO.removeTimeList("dummy2", "dummy2");
        userDAO.removeUser("dummy2");
    }

    @Test
    public void removeTimeList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", "dummy2", 60, 0, 0));

        assertTrue(timeListDAO.removeTimeList("dummy2", "dummy2"));

        //clean up
        userDAO.removeUser("dummy2");
    }


    //
    //shiftlist-test
    //
    @Test
    public void getShiftLists(){
        assertNotNull(shiftListDAO.getShiftLists());
    }

    @Test
    public void getShiftListById(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListDAO.getShiftListById("dummy3", 1));

        //clean up
        shiftListDAO.removeShiftlist("dummy3", 1);
        userDAO.removeUser(("dummy3"));

    }

    @Test
    public void createShiftList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));

        assertTrue(shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true)));

        //clean up
        shiftListDAO.removeShiftlist("dummy3", 1);
        userDAO.removeUser(("dummy3"));
    }


    @Test
    public void updateShiftList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        assertTrue(shiftListDAO.updateShiftlist(new ShiftList("dummy3", 2, true, new Date(2017-01-02), 1, false)));

        //clean up
        shiftListDAO.removeShiftlist("dummy3", 2);
        userDAO.removeUser(("dummy3"));
    }


    @Test
    public void removeShiftList(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        //clean up and test
        assertTrue(shiftListDAO.removeShiftlist("dummy3", 1));

        userDAO.removeUser(("dummy3"));
    }



}