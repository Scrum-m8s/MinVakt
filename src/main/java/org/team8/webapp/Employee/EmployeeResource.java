package org.team8.webapp.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 */

@Path("/employees")
public class EmployeeResource {
    EmployeeDAO dao = new EmployeeDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getEmployees() {
        System.out.println("getEmployees");
        return dao.getEmployees();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Employee getEmployee(@PathParam("id") String id) {
        System.out.println("getEmployeeById: " + id);
        return dao.getEmployeeById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createEmployee(Employee e) {
        System.out.println("createEmployee");
        return dao.createEmployee(e);
    }

    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateEmployee(Employee e) {
        System.out.println("updateEmployee");
        return dao.updateEmployee(e);
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeEmployee(@PathParam("id") String id) {
        dao.removeEmployee(id);
    }
}
