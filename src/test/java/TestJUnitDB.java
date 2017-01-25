import org.junit.BeforeClass;
import org.junit.Test;
import org.team8.webapp.Busy.Busy;
import org.team8.webapp.Busy.BusyDAO;
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.Employee.Employee;
import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.Shift.Shift;
import org.team8.webapp.Shift.ShiftDAO;
import org.team8.webapp.ShiftList.ShiftList;
import org.team8.webapp.ShiftList.ShiftListDAO;
import org.team8.webapp.TimeList.TimeListDAO;
import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
            userDAO.removeUser("dummy");
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
        userDAO.createUser(new User("dummy", "dummy", 1));

        assertTrue(userDAO.removeUser("dummy"));
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
    public void getEmployeeByCategory() {
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));

        //creating dummy employee
        employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

        assertNotNull(employeeDAO.getEmployeeByCategory(1));

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
            employeeDAO.removeEmployee("dummy");
            userDAO.removeUser("dummy");
        }
    }

    @Test
    public void updateEmployee() {
        try {
            //creating dummy user to fetch
            userDAO.createUser(new User("dummy", "dummy", 1));

            //creating dummy employee
            employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

            assertTrue(employeeDAO.updateEmployee(new Employee("dummy", "dummyUpdated", "dummyUpdated", "dummyUpdated", "dummyUpdated", 1)));
        }
        catch (Exception ex){
            System.err.println("Issue with database connection.");
            ex.printStackTrace();
        }
        finally {
            //clean up
            employeeDAO.removeEmployee("dummy");
            userDAO.removeUser("dummy");
        }

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
            shiftDAO.removeShift(4);
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
        shiftDAO.removeShift(updated.getShift_id());
    }

    @Test
    public void removeShift(){
        //creating dummy data to fetch
        Shift dummy = new Shift(4, 1, 1, 1);
        shiftDAO.createShift(dummy);

        //clean up and test
        assertTrue(shiftDAO.removeShift(dummy.getShift_id()));
    }
  
  
    //
    //Busy-tests
    //
    @Test
    public void getBusy(){
        assertNotNull(busyDAO.getBusy());
    }

    @Test
    public void getBusyById(){
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));


        assertNotNull(busyDAO.getBusyById("dummy"));

        //clean up
        busyDAO.removeBusy("dummy", 1);
        userDAO.removeUser("dummy");
    }

    @Test
    public void getSingleBusy() {
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));

        assertNotNull(busyDAO.getSingleBusy("dummy", 1));

        //clean up
        busyDAO.removeBusy("dummy", 1);
        userDAO.removeUser("dummy");
    }
  
    @Test
    public void createBusy() {
      try {
          //dummy data
          userDAO.createUser(new User("dummy", "dummy", 1));
          assertTrue(busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07))));
      }
      catch (Exception e){
          System.err.println("Issue with database connection.");
          e.printStackTrace();
      }
      finally {
          //removing test data after tests to avoid clutter in database
          busyDAO.removeBusy("dummy", 1);
          userDAO.removeUser("dummy");
      }
  }
/*
  @Test
  public void updateBusy() {
      //dummy data and dates
      Date date = new Date(2017-11-12);
      Date dateUpdated = new Date(2016-01-06);

      userDAO.createUser(new User("dummy", "dummy", 1));
      busyDAO.createBusy(new Busy("dummy", 1, date));

      assertTrue(busyDAO.updateBusy(new Busy("dummy", 1, dateUpdated)));

      //clean up
      busyDAO.removeBusy("dummy", 1);
      userDAO.removeUser("dummy");
  }

  @Test
  public void removeBusy() {
      //dummy data and dates
      Date date = new Date(2017-11-12);

      userDAO.createUser(new User("dummy", "dummy", 1));
      busyDAO.createBusy(new Busy("dummy", 1, date));

      assertTrue(busyDAO.removeBusy("dummy", 1));

      //clean up
      userDAO.removeUser("dummy");
  }


    //
    //Timelist-tests
    //
    @Test
    public void getTimeLists(){
        assertNotNull(timeListDAO.getTimeLists());
    }

    @Test
    public void getTimeListsById(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0));

        assertNotNull(timeListDAO.getTimeListsById("dummy"));

        //clean up
        timeListDAO.removeTimeList("dummy", 1990,0);
        userDAO.removeUser("dummy");
    }

    @Test
    public void getSingleTimeList() {
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0));

        assertNotNull(timeListDAO.getSingleTimeList("dummy", 1990, 0));

        //clean up
        timeListDAO.removeTimeList("dummy", 1990, 0);
        userDAO.removeUser("dummy");
    }

    @Test
    public void createTimelist(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        assertTrue(timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0)));

        //clean up
        timeListDAO.removeTimeList("dummy", 1990, 0);
        userDAO.removeUser(("dummy"));
    }

    @Test
    public void updateTimeList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", 1990, 0, 60, 0, 0));

        assertTrue(timeListDAO.updateTimeList(new TimeList("dummy2", 1990, 0, 65, 1, 1)));

        //clean up
        timeListDAO.removeTimeList("dummy2", 1990, 0);
        userDAO.removeUser("dummy2");
    }

    @Test
    public void removeTimeList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", 1990, 0, 60, 0, 0));

        assertTrue(timeListDAO.removeTimeList("dummy2", 1990, 0));

        //clean up
        userDAO.removeUser("dummy2");
    }


    //
    //Shiftlist-test
    //
    @Test
    public void getShiftLists(){
        assertNotNull(shiftListDAO.getShiftLists());
    }

    @Test
    public void getShiftListById(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListDAO.getShiftListsById("dummy3"));

        //clean up
        shiftListDAO.removeShiftlist("dummy3", 1);
        userDAO.removeUser(("dummy3"));

    }

    @Test
    public void getSingleShift(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy", 2, false, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListDAO.getSingleShift("dummy", 2));

        //clean up
        shiftListDAO.removeShiftlist("dummy", 2);
        userDAO.removeUser(("dummy"));
    }

    @Test
    public void getWantSwap() {
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy", 2, true, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListDAO.getWantSwap(true));

        //clean up
        shiftListDAO.removeShiftlist("dummy", 2);
        userDAO.removeUser(("dummy"));
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

        assertTrue(shiftListDAO.updateShiftlist(new ShiftList("dummy3", 1, true, new Date(2015-01-02), 50, false)));

        //clean up
        shiftListDAO.removeShiftlist("dummy3", 1);
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
    }*/

    @Test
    public void testShiftList() {
        ArrayList<ShiftList> shiftLists;
        ArrayList<ShiftList> want_swap_list = new ArrayList<>();
        shiftLists = shiftListDAO.getShiftLists();
        for (int i =0; i < shiftLists.size(); i++) {
            if(shiftLists.get(i).isWant_swap() == true) {
                want_swap_list.add(shiftLists.get(i));
            }
        }
        System.out.println("Henter ut alle user_ids hvor want_swap er true med vanlig get metode:");
        for (int i =0; i < want_swap_list.size(); i++) {
            System.out.println(want_swap_list.get(i).getUser_id());
        }

        //System.out.println(shiftLists.get(1).isWant_swap());
        //assertTrue(shiftListDAO.updateShiftlist(new ShiftList("nina", 1, false, new Date(2017-01-01), 0, false)));
    }

}