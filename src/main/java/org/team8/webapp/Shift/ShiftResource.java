package org.team8.webapp.Shift;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by asdfLaptop on 11.01.2017.
 */

@Path("/shifts")
public class ShiftResource {
    ShiftDAO dao = new ShiftDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Shift> getShifts() {
        System.out.println("getShifts");
        return dao.getShifts();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Shift getEmployee(@PathParam("id") String id) {
        System.out.println("getShiftById: " + id);
        return dao.getShiftById(id);
    }

    //TODO: Add create, update, remove. Look at EmployeeResource for guidelines.

}
