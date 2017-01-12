package org.team8.webapp.Busy;

/**
 * Created by Nina on 12.01.2017.
 */

import org.team8.webapp.Employee.Employee;
import org.team8.webapp.User.User;

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
            return dao.getAllBusy();
        }
        @Path("{user_id}")
        @GET
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Busy getBusyByUserId(@PathParam("user_id") String user_id) {
            System.out.println("getBusyUserId: " + user_id);
            return dao.getBusyByUserId(user_id);
        }

        @Path("{busy_id}")
        @GET
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Busy getBusyByShiftId(@PathParam("busy_id") int shiftID) {
            System.out.println("getBusyShiftId: " + shiftID);
            return dao.getBusyByShiftId(shiftID);
        }

        @POST
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public boolean createBusy(Busy b) {
        System.out.println("createBusy");
        return dao.createBusy(b);
        }
}