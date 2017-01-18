package org.team8.webapp.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 10.01.2017.
 * Edited by MisterEaster on 18.01.2017.
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

    @Path("get_available_employees")
    @GET
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getAvailableEmployees(int shift_id, Date my_date, int category){
        System.out.println("getAvailableEmployees");
        return dao.getAvailableEmployees(shift_id,my_date,category);
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Employee getEmployee(@PathParam("id") String id) {
        System.out.println("getEmployeeById: " + id);
        return dao.getEmployeeById(id);
    }

    @Path("/current")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Employee getCurrentEmployee(@Context SecurityContext sc){
        System.out.println("getCurrentEmployee");
        return dao.getEmployeeById(sc.getUserPrincipal().getName());
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createEmployee(Employee e) {
        System.out.println("createEmployee");
        return dao.createEmployee(e);
    }

    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateEmployee(Employee e) {
        System.out.println("updateEmployee");
        return dao.updateEmployee(e);
    }

    @Path("{id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeEmployee(@PathParam("id") String id) {
        dao.removeEmployee(id);
    }
}
