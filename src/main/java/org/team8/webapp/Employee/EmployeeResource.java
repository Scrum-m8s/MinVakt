package org.team8.webapp.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Path("{shift_id}/{my_date}/{category}")
    @GET
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getAvailableEmployees(@PathParam("shift_id") int shift_id, @PathParam("my_date") String my_date, @PathParam("category") int category) throws ParseException {
        System.out.println("getAvailableEmployees");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date my_date_util = sdf.parse(my_date);
        java.sql.Date my_date_sql = new java.sql.Date(my_date_util.getTime());
        return dao.getAvailableEmployees(shift_id,my_date_sql,category);
    }

    @Path("available_employees")
    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Employee> getAvailableEmployees(AvailableEmployees e) {
        System.out.println("getAvailableEmployees");
        int category = e.getCategory();
        Date my_date = e.getMy_date();
        int shift_id = e.getShift_id();
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
