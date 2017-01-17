package org.team8.webapp.Busy;

/**
 * Created by Nina on 12.01.2017.
 * Edited by Mr_Easter on 12.01.2017
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/busy")
public class BusyResource{
        BusyDAO dao = new BusyDAO();

        @GET
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public ArrayList<Busy> getBusy() {
            System.out.println("getBusy");
            return dao.getBusy();
        }
        @Path("{user_id}/{shift_id}")
        @GET
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Busy getBusyByUserId(@PathParam("user_id") String user_id, @PathParam("shift_id") int shift_id) {
            System.out.println("getBusyUserId: " + user_id + " " + shift_id);
            return dao.getBusyByUserIdAndShiftId(user_id,shift_id);
        }

        @POST
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public boolean createBusy(Busy b) {
        System.out.println("createBusy");
        return dao.createBusy(b);
        }
        
        @Path("{user_id}/{shift_id}")
        @DELETE
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public void removeBusy(@PathParam("user_id") String user_id, @PathParam("shift_id") int shift_id) {
            dao.removeBusy(user_id, shift_id);
    }
}