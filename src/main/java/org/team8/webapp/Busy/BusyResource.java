package org.team8.webapp.Busy;

/**
 * Created by Nina on 12.01.2017.
 * Edited by Mr_Easter on 12.01.2017
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Path("/busy")
public class BusyResource{
        BusyDAO dao = new BusyDAO();

    private static final String SUCCESS_RESULT="<result>success</result>";
    private static final String FAILURE_RESULT="<result>failure</result>";
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Busy> getBusy() {
            System.out.println("getBusy");
            return dao.getBusy();
    }

    @Path("{user_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Busy> getBusyByUserId(@PathParam("user_id") String user_id) {
            System.out.println("getBusy");
            return dao.getBusyById(user_id);
    }

    @Path("{my_date}/{shift_id}/{user_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Busy getSingleBusy(@PathParam("my_date") Date my_date, @PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id) {
            System.out.println("getSingleBusy: " + user_id + " " + shift_id);
            return dao.getSingleBusy(my_date, shift_id, user_id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createBusy(Busy b) {
            System.out.println("createBusy");
            return dao.createBusy(b);
    }

    //@Path("{my_date}/{shift_id}/{user_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateBusy(ArrayList<Busy> busies) {
            return dao.updateBusy(busies);
    }

    @Path("{my_date}/{shift_id}/{user_id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String removeBusy(@PathParam("my_date") Date my_date, @PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id) {
            boolean result = dao.removeBusy(my_date, shift_id, user_id);
            if (result){return SUCCESS_RESULT;}
        return FAILURE_RESULT;
    }
}