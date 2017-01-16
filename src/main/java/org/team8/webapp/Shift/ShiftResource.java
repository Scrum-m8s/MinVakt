package org.team8.webapp.Shift;

import javax.ws.rs.*;
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

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createShift(Shift e) {
        System.out.println("createShift");
        return dao.createShift(e);
    }

    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShift(Shift e) {
        System.out.println("updateShift");
        return dao.updateShift(e);
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeShift(@PathParam("id") String id) {
        dao.removeShift(id);
    }
}
