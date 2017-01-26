package org.team8.webapp.ShiftSwitch;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Mr.Easter on 24/01/2017.
 */

//TODO: Implemented only if there's enough time. Can replace want_swap to enable switching with a particular person.
@Path("/shiftswitch")
public class ShiftSwitchResource {
    ShiftSwitchDAO dao = new ShiftSwitchDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftSwitch> getShiftSwitches() {
        System.out.println("getUsers");
        return dao.getShiftSwitches();
    }

    @Path("{my_date}/{shift_id}/{user_id_from}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ShiftSwitch getSingleShiftSwitch(@PathParam("my_date") Date my_date,@PathParam("shift_id") int shift_id, @PathParam("user_id_from") String user_id_from) {
        System.out.println("getSingleShiftSwitch");
        return dao.getSingleShiftSwitch(my_date,shift_id,user_id_from);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createShiftSwitch(ShiftSwitch e) {
        System.out.println("createShiftSwitch");
        return dao.createShiftSwitch(e);
    }

    @Path("{my_date}/{shift_id}/{user_id_from}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftSwitch(ShiftSwitch e) {
        System.out.println("updateShiftSwitch");
        return dao.updateShiftSwitch(e);
    }

    @Path("{my_date}/{shift_id}/{user_id_from}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeUser(@PathParam("my_date") Date my_date,@PathParam("shift_id") int shift_id, @PathParam("user_id_from") String user_id_from) {
        dao.removeShiftSwitch(my_date, shift_id, user_id_from);
    }
}
