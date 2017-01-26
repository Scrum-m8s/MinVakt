import org.junit.BeforeClass;
import org.junit.Test;
import org.team8.webapp.Busy.BusyDAO;
import org.team8.webapp.Busy.BusyResource;
import org.team8.webapp.Database.DatabaseManagement;
import org.team8.webapp.Employee.AvailableEmployeeResource;
import org.team8.webapp.Employee.Employee;
import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.Employee.EmployeeResource;
import org.team8.webapp.Shift.ShiftDAO;
import org.team8.webapp.Shift.ShiftResource;
import org.team8.webapp.ShiftList.ShiftListDAO;
import org.team8.webapp.ShiftList.ShiftListResource;
import org.team8.webapp.TimeList.TimeListDAO;
import org.team8.webapp.TimeList.TimeListResource;
import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;
import org.team8.webapp.User.UserResource;

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

    private static BusyResource busyResource;
    private static ShiftResource shiftResource;
    private static EmployeeResource employeeResource;
    private static UserResource userResource;
    private static TimeListResource timeListResource;
    private static ShiftListResource shiftListResource;
    private static AvailableEmployeeResource availableEmployeeResource;


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

            busyResource = new BusyResource();
            shiftResource = new ShiftResource();
            employeeResource = new EmployeeResource();
            userResource = new UserResource();
            timeListResource = new TimeListResource();
            shiftListResource = new ShiftListResource();

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
        assertNotNull(userResource.getUsers());
    }
    @Test
    public void getUserById() {
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));

        //test fetching of data
        assertNotNull(userResource.getUser("dummy"));

        //clean up
        userDAO.removeUser("dummy");
    }
    @Test
    public void getUserByIdCatch() {
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));

        //test fetching of data
        assertNull(userResource.getUser("notdummy"));

        //clean up
        userDAO.removeUser("dummy");
    }
    @Test
    public void createUser() {

        //creates dummy user
        assertTrue(userResource.createUser(new User("dummy", "dummy", 1)));

        //clean up
        userDAO.removeUser("dummy");

    }
    @Test
    public void createUserCatch() {

        //creates dummy user
        assertFalse(userResource.createUser(new User("Dumbdummy, but it has way too long of a name. Seriously!", "dummy", 1)));

    }
    @Test
    public void updateUser() {
        //creating dummy user to update
        userDAO.createUser(new User("dummy", "dummy", 1));

        assertTrue(userResource.updateUser(new User("dummy", "dummyUPDATED", 2)));

        //clean up
        userDAO.removeUser("dummy");
    }
    @Test
    public void updateUserCatch() {
        //creating dummy user to update
        userDAO.createUser(new User("dummy", "dummy", 1));

        assertTrue(userDAO.updateUser(new User("notdummy", "dummyUPDATED", 2)));

        //clean up
        userDAO.removeUser("dummy");
    }
    @Test
    public void removeUser() {
        //Create dummy user to delete
        userDAO.createUser(new User("dummy", "dummy", 1));

        assertTrue(userDAO.removeUser("dummy"));
    }
    @Test
    public void removeUserCatch() {
        //Create dummy user to delete
        userDAO.createUser(new User("dummy", "dummy", 1));

        assertFalse(userDAO.removeUser("notdummy"));

        //clean up
        userDAO.removeUser("dummy");
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

/*
    @Test
    //funker ikke
    public void  getAvailableEmployees(){
        //creating dummy user to fetch
       // userDAO.createUser(new User("dummy", "dummy", 1));
        //creating dummy employee
   //     employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

        ArrayList<Employee> e = availableEmployeeResource.getAvailableEmployees(1,"2017-01-01",1);

        //assertNotNull(e);

        //clean up
       // employeeDAO.removeEmployee("dummy");
        //userDAO.removeUser("dummy");
    }
*/
    @Test
    public void getEmployeesForDate(){
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        //creating dummy employee
        employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

        assertNotNull(employeeDAO.getEmployeesForDate("dummy"));

        //clean up
        employeeDAO.removeEmployee("dummy");
        userDAO.removeUser("dummy");
    }

    @Test
    public void getEmployeesForShift(){
        //creating dummy user to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        //creating dummy employee
        employeeDAO.createEmployee(new Employee("dummy", "dummy", "dummy", "dummy", "dummy", 1));

        assertNotNull(employeeDAO.getEmployeesForShift(1, "dummy"));

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



/*
    //
    //Busy-tests
    //
    @Test
    public void getBusy(){
        assertNotNull(busyResource.getBusy());
    }
    @Test
    public void getBusyById(){
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));


        assertNotNull(busyResource.getBusyByUserId("dummy"));

        //clean up
        busyDAO.removeBusy(new Date(1970-05-07), 1, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void getBusyByIdCatch(){
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));


        assertNull(busyResource.getBusyByUserId("notdummy"));

        //clean up
        busyDAO.removeBusy(new Date(1970-05-07), 1, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void getSingleBusy() {
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));

        assertNotNull(busyResource.getSingleBusy(new Date(1970-05-07), 1, "dummy"));

        //clean up
        busyDAO.removeBusy(new Date(1970-05-07), 1, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void getSingleBusyCatch() {
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, new Date(1970-05-07)));

        assertNull(busyResource.getSingleBusy(new Date(1970-05-07), 1, "notdummy"));

        //clean up
        busyDAO.removeBusy(new Date(1970-05-07), 1, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void createBusy() {
        try {
            //dummy data
            userDAO.createUser(new User("dummy", "dummy", 1));
            assertTrue(busyResource.createBusy(new Busy("dummy", 1, new Date(1970-05-07))));
        }
        catch (Exception e){
            System.err.println("Issue with database connection.");
            e.printStackTrace();
        }
        finally {
            //removing test data after tests to avoid clutter in database
            busyDAO.removeBusy(new Date(1970-05-07), 1, "dummy");
            userDAO.removeUser("dummy");
        }
    }
    @Test
    public void createBusyCatch() {
        //dummy data
        userDAO.createUser(new User("dummy", "dummy", 1));
        assertFalse(busyResource.createBusy(new Busy("Dumbdummy, but it has way too long of a name. Seriously!", 1, new Date(1970-05-07))));

        //removing test data after tests to avoid clutter in database
        userDAO.removeUser("dummy");
    }
    @Test
    public void updateBusy() {
        //dummy data and dates
        Date date = new Date(2017-11-12);
        Date dateUpdated = new Date(2016-01-06);

        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, date));

        assertTrue(busyResource.updateBusy(new Busy("dummy", 1, dateUpdated)));

        //clean up
        busyDAO.removeBusy(dateUpdated, 1, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void updateBusyCatch() {
        //dummy data and dates
        Date date = new Date(2017-11-12);
        Date dateUpdated = new Date(2016-01-06);

        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, date));

        assertFalse(busyResource.updateBusy(new Busy("Dumbdummy, but it has way too long of a name. Seriously!", 1, dateUpdated)));

        //clean up
        busyDAO.removeBusy(date, 1, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void removeBusy() {
        //dummy data and dates
        Date date = new Date(2017-11-12);

        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, date));

        assertTrue(busyResource.removeBusy(date, 1,"dummy"));

        //clean up
        userDAO.removeUser("dummy");
    }
    @Test
    public void removeBusyCatch() {
        //dummy data and dates
        Date date = new Date(2017-11-12);

        userDAO.createUser(new User("dummy", "dummy", 1));
        busyDAO.createBusy(new Busy("dummy", 1, date));

        assertFalse(busyResource.removeBusy(date, 1,"notdummy"));

        //clean up
        busyResource.removeBusy(date, 1, "dummy");
        userDAO.removeUser("dummy");
    }


    //
    //Timelist-tests
    //
    @Test
    public void getTimeLists(){
        assertNotNull(timeListResource.getTimeLists());
    }
    @Test
    public void getTimeListsById(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0));

        assertNotNull(timeListDAO.getTimeListsById("dummy"));

        //clean up
        timeListDAO.removeTimeList(1990,0, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void getTimeListsByIdCatch(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0));

        assertNull(timeListDAO.getTimeListsById("notdummy"));

        //clean up
        timeListDAO.removeTimeList(1990,0, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void getSingleTimeList() {
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0));

        assertNotNull(timeListResource.getSingleTimeList(1990, 0, "dummy"));

        //clean up
        timeListDAO.removeTimeList(1990, 0, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void getSingleTimeListCatch() {
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        timeListDAO.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0));

        assertNull(timeListResource.getSingleTimeList(1990, 0, "notdummy"));

        //clean up
        timeListDAO.removeTimeList(1990, 0, "dummy");
        userDAO.removeUser("dummy");
    }
    @Test
    public void createTimelist(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        assertTrue(timeListResource.createTimeList(new TimeList("dummy", 1990, 0, 60, 0, 0)));

        //clean up
        timeListDAO.removeTimeList(1990, 0, "dummy");
        userDAO.removeUser(("dummy"));
    }
    @Test
    public void createTimelistCatch(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        assertFalse(timeListResource.createTimeList(new TimeList("Dumbdummy, but it has way too long of a name. Seriously!", 1990, 0, 60, 0, 0)));

        //clean up
        userDAO.removeUser(("dummy"));
    }
    @Test
    public void updateTimeList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", 1990, 0, 60, 0, 0));

        assertTrue(timeListResource.updateTimeList(new TimeList("dummy2", 1990, 0, 65, 1, 1)));

        //clean up
        timeListDAO.removeTimeList(1990, 0, "dummy");
        userDAO.removeUser("dummy2");
    }
    @Test
    public void updateTimeListCatch(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", 1990, 0, 60, 0, 0));

        assertFalse(timeListResource.updateTimeList(new TimeList("notdummy2", 1990, 0, 65, 1, 1)));

        //clean up
        timeListDAO.removeTimeList(1990, 0, "dummy");
        userDAO.removeUser("dummy2");
    }
    @Test
    public void removeTimeList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", 1990, 0, 60, 0, 0));

        assertTrue(timeListResource.removeTimeList(1990, 0, "dummy2"));

        //clean up
        userDAO.removeUser("dummy2");
    }
    @Test
    public void removeTimeListCatch(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy2", "dummy2", 1));
        timeListDAO.createTimeList(new TimeList("dummy2", 1990, 0, 60, 0, 0));

        assertFalse(timeListResource.removeTimeList(1990, 0, "notdummy2"));

        //clean up
        timeListDAO.removeTimeList(1990, 0, "dummy2");
        userDAO.removeUser("dummy2");
    }

    //
    //Shiftlist-test
    //
    @Test
    public void getShiftLists(){
        assertNotNull(shiftListResource.getShiftLists());
    }
    @Test
    public void getShiftListById(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListResource.getShiftListById("dummy3"));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01),1,"dummy3");
        userDAO.removeUser(("dummy3"));

    }
    @Test
    public void getShiftListByIdCatch(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListResource.getShiftListById("notdummy3"));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01),1,"dummy3");
        userDAO.removeUser(("dummy3"));

    }
    @Test
    public void getSingleShift(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy", 2, false, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListResource.getSingleShift(new Date(2017-01-01), 2, "dummy"));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01), 2, "dummy");
        userDAO.removeUser(("dummy"));
    }
    @Test
    public void getSingleShiftCatch(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy", 2, false, new Date(2017-01-01), 0, true));

        assertNull(shiftListResource.getSingleShift(new Date(2017-01-01), 2, "notdummy"));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01), 2, "dummy");
        userDAO.removeUser(("dummy"));
    }
    @Test
    public void getWantSwap() {
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy", "dummy", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy", 2, true, new Date(2017-01-01), 0, true));

        assertNotNull(shiftListResource.getWantSwap(true));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01), 2, "dummy");
        userDAO.removeUser(("dummy"));
    }
    @Test
    public void createShiftList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));

        assertTrue(shiftListResource.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true)));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01), 1, "dummy3");
        userDAO.removeUser(("dummy3"));
    }
    @Test
    public void createShiftListCatch(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));

        assertFalse(shiftListResource.createShiftlist(new ShiftList("Dumbdummy, but it has way too long of a name. Seriously!", 1, false, new Date(2017-01-01), 0, true)));

        //clean up
        userDAO.removeUser(("dummy3"));
    }

    @Test
    public void updateShiftList(){
        // creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        assertTrue(shiftListDAO.updateShiftlist(new ShiftList("dummy3", 1, true, new Date(2015-01-02), 50, false)));

        //clean up
        shiftListDAO.removeShiftlist(new Date(2017-01-01), 1, "dummy3");
        userDAO.removeUser(("dummy3"));
    }


    @Test
    public void removeShiftList(){
        //creating dummy data to fetch
        userDAO.createUser(new User("dummy3", "dummy3", 1));
        shiftListDAO.createShiftlist(new ShiftList("dummy3", 1, false, new Date(2017-01-01), 0, true));

        //clean up and test
        assertTrue(shiftListDAO.removeShiftlist(new Date(2017-01-01), 1, "dummy3"));

        userDAO.removeUser(("dummy3"));
    }

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
*/
}