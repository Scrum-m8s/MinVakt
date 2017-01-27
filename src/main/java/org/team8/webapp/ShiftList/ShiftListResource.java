package org.team8.webapp.ShiftList;

import org.team8.webapp.TimeList.TimeListDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by mariyashchekanenko on 12/01/2017.
 * Edited by Mr_Easter on 12/01/2017 and 18.01.2017.
 */
@Path("/shift_lists/")
public class ShiftListResource {
    ShiftListDAO dao = new ShiftListDAO();
    TimeListDAO tdao = new TimeListDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getShiftLists() {
        System.out.println("getShiftLists");
        return dao.getShiftLists();
    }

    @Path("get_by_id/{user_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getShiftListById(@PathParam("user_id") String user_id){
        System.out.println("getShiftListById");
        return dao.getShiftListsById(user_id);
    }


    @Path("want_swap/{want_swap}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getWantSwap(@PathParam("want_swap") boolean swap){
        System.out.println("getWantSwap");
        return dao.getWantSwap(swap);
    }

    @Path("{my_date}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getShiftListsByDate(@PathParam("my_date") Date my_date){
        System.out.println("getShiftListByDate");
        return dao.getShiftListsByDate(my_date);
    }

    @Path("{my_date}/{shift_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getShiftListsByDateAndShiftId(@PathParam("my_date") Date my_date, @PathParam("shift_id") int shift_id){
        System.out.println("getShiftListByDateAndId");
        return dao.getShiftListsByDateAndShiftId(my_date, shift_id);
    }

    @Path("{my_date}/{shift_id}/{user_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ShiftList getSingleShift(@PathParam("my_date") Date my_date, @PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id){
        System.out.println("getShiftListById");
        return dao.getSingleShift(my_date, shift_id, user_id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createShiftlist(ShiftList s_l) {
        System.out.println("create Shift_list");

        boolean result = dao.createShiftlist(s_l);
        if (result){tdao.onShiftListCreate(s_l);}
        return result;
    }

    @Path("deviance")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean registerDeviance(ShiftList s_l) {
        System.out.println("register deviance");
        boolean result = dao.registerDeviance(s_l);
        if (result){tdao.onShiftListDevianceUpdate(s_l);}
        return result;
    }


    @Path("{my_date}/{shift_id}/{user_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftlist(ShiftList s_l){
        System.out.println("update Shift_list");
        return dao.updateShiftlist(s_l);
    }

    @Path("{new_user_id}/{my_date}/{shift_id}/{user_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftByUserId(@PathParam("new_user_id") String new_user_id, @PathParam("my_date") Date my_date, @PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id){
        System.out.println("update Shift_list");
        return dao.updateShiftByUserId(new_user_id, user_id, shift_id, my_date);
    }

    @Path("{my_date}/{shift_id}/{user_id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeShiftlist(@PathParam("my_date") Date my_date, @PathParam("shift_id") int shift_id, @PathParam("user_id") String user_id) {
        System.out.println("remove Shift_list");
        boolean result = dao.removeShiftlist(my_date, shift_id, user_id);
        if (result){tdao.onShiftListRemove(my_date,shift_id,user_id);}
        return result;
    }
}
