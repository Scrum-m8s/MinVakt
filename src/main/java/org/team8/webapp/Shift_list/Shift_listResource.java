package org.team8.webapp.Shift_list;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by mariyashchekanenko on 12/01/2017.
 * Edited by Mr_Easter on 12/01/2017.
 */
@Path("/shift_lists")
public class Shift_listResource {
    Shift_listDAO dao = new Shift_listDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Shift_list> getShiftLists() {
        System.out.println("getShiftLists");
        return dao.getShiftLists();
    }

    @Path("/{shift_id}/{user_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Shift_list getShiftListById(@PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id){
        System.out.println("getShiftListById");
        return dao.getShiftListById(user_id, shift_id);
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createShiftlist(Shift_list s_l) {
        System.out.println("create Shift_list");
        return dao.createShiftlist(s_l);
    }

    @Path("/{shift_id}/{user_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftlist(Shift_list s_l){
        System.out.println("update Shift_list");
        return dao.updateShiftlist(s_l);
    }

    @Path("/{shift_id}/{user_id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeShiftlist(@PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id){
        System.out.println("remove Shift_list");
        return dao.removeShiftlist(user_id, shift_id);
    }

}
