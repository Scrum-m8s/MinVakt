package org.team8.webapp.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;
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

    @Path("shift/{date}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getEMployeesForDate(@PathParam("date") String date){
        System.out.println("getEmployeesForDate: " + date);
        return dao.getEmployeesForDate(date);
    }

    @Path("/shift/{date}/{shift}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getEmployeesForShift(@PathParam("date") String date, @PathParam("shift") int shift_id){
        System.out.println("getEmployeesForShift: " + date + ", " + shift_id);
        return dao.getEmployeesForShift(shift_id, date);
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Employee getEmployee(@PathParam("id") String id) {
        System.out.println("getEmployeeById: " + id);
        return dao.getEmployeeById(id);
    }

    @Path("category/{category}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getEmployeeCategory(@PathParam("category") int category) {
        System.out.println("getEmployeeByCategory: " + category);
        return dao.getEmployeeByCategory(category);
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
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeEmployee(@PathParam("id") String id) {
        return dao.removeEmployee(id);
    }
}
