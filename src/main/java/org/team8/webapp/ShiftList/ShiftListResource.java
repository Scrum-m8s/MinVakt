package org.team8.webapp.ShiftList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by mariyashchekanenko on 12/01/2017.
 * Edited by Mr_Easter on 12/01/2017 and 18.01.2017.
 */
@Path("/shift_lists/")
public class ShiftListResource {
    ShiftListDAO dao = new ShiftListDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getShiftLists() {
        System.out.println("getShiftLists");
        return dao.getShiftLists();
    }

    @Path("{user_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getShiftListById(@PathParam("user_id") String user_id){
        System.out.println("getShiftListById");
        return dao.getShiftListsById(user_id);
    }

    @Path("{user_id}/{shift_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ShiftList getSingleShift(@PathParam("user_id") String user_id, @PathParam("shift_id") int shift_id){
        System.out.println("getShiftListById");
        return dao.getSingleShift(user_id, shift_id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createShiftlist(ShiftList s_l) {
        System.out.println("create Shift_list");
        return dao.createShiftlist(s_l);
    }

    @Path("{user_id}/{shift_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftlist(ShiftList s_l){
        System.out.println("update Shift_list");
        return dao.updateShiftlist(s_l);
    }

    @Path("/{user_id}/{shift_id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeShiftlist(@PathParam("user_id") String user_id, @PathParam("shift_id") int shift_id){
        System.out.println("remove Shift_list");
        return dao.removeShiftlist(user_id, shift_id);
    }
}
