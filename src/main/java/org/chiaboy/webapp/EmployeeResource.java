package org.chiaboy.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 */

@Path("/employees")
public class EmployeeResource {
    EmployeeDAO dao = new EmployeeDAO();

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public ArrayList<Employee> getEmployees() {
        return dao.getEmployees();
    }
}
