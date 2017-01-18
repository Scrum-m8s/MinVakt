package org.team8.webapp.ShiftList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by mariyashchekanenko on 12/01/2017.
 * Edited by Mr_Easter on 12/01/2017.
 */
@Path("/shift_lists")
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
        return dao.getShiftListById(user_id);
    }

    @Path("{user_id}/{shift_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ShiftList getShiftListById(@PathParam("user_id") String user_id, @PathParam("shift_id") int shift_id){
        System.out.println("getShiftListById");
        return dao.getSingleShift(user_id, shift_id);
    }
    
    @Path("/want_swap")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getWantSwap(){
        System.out.println("getWantSwap");
        return dao.getWantSwap();
    }

    @Path("/{user_id}/{shift_id}/{want_swap}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean Want_swap(ShiftList s_l) {
        System.out.println("createWantSwap");
        return dao.wantSwap(s_l);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createShiftlist(ShiftList s_l) {
        System.out.println("create Shift_list");
        return dao.createShiftlist(s_l);
    }

    @Path("/{shift_id}/{user_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftlist(ShiftList s_l){
        System.out.println("update Shift_list");
        return dao.updateShiftlist(s_l);
    }

    @Path("/{shift_id}/{user_id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeShiftlist(@PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id){
        System.out.println("remove Shift_list");
        return dao.removeShiftlist(user_id, shift_id);
    }

}
