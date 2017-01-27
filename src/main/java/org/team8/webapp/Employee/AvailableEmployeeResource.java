package org.team8.webapp.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 * Edited by MisterEaster on 18.01.2017.
 */

//Finds available employees based on shift_id, my_date and category.
@Path("/availableemployees")
public class AvailableEmployeeResource{
    EmployeeDAO dao = new EmployeeDAO();


    //Parses JSON-data to an AvailableEmployee object. Accepts date as a string in "yyyy-MM-dd"-format.
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getAvailableEmployees(AvailableEmployee ae) {
        System.out.println("getAvailableEmployees");

        //Parses date-string to SQLDate format.
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date my_date_util = null;
        try {
            my_date_util = sdf1.parse(ae.getMy_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date my_date = new java.sql.Date(my_date_util.getTime());

        return dao.getAvailableEmployees(ae.getShift_id(),my_date,ae.getCategory());
    }
}
