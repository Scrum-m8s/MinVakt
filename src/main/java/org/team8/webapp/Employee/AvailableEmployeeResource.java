package org.team8.webapp.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 * Edited by MisterEaster on 18.01.2017.
 */

@Path("/availableemployees")
public class AvailableEmployeeResource{
    EmployeeDAO dao = new EmployeeDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getAvailableEmployees() {
        System.out.println("getAvailableEmployees");
        return dao.getAvailableEmployees(1,"2017-01-10",1);
    }
}
