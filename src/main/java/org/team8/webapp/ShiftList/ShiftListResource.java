package org.team8.webapp.ShiftList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by mariyashchekanenko on 12/01/2017.
 * Edited by Mr_Easter on 12/01/2017.
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
        return dao.getShiftListById(user_id);
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

    @Path("deviance/{user_id}/{shift_id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateShiftListDeviance(ShiftList s_l){
        System.out.println("update deviance in Shift_list " +  s_l.getDeviance());
        return dao.updateShiftListDeviance(s_l);
    }




    @Path("/function/update/deviances")
    @GET
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    //@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String updateDeviance() {
        System.out.println("update Deviance in Shift_list");
        ArrayList<ShiftList> listen = getShiftLists();
        String month = "Januar";                        //TODO: Gj�r denne verdien redigerbar.
        String message="";
        boolean result = false;
        for (int i=0;i<listen.size();i++){
            if (listen.get(i).getDeviance()!=0){
                result = dao.updateDeviance(listen.get(i),month);
                message+="User "+listen.get(i).getUser_id()+" has deviance "+listen.get(i).getDeviance()+" added.\n";
                if (result){dao.removeDeviance(listen.get(i).getUser_id(),listen.get(i).getShift_id());}
            }
        }
        return "Deviances updated. \n" + message;
    }


    @Path("{user_id}/{shift_id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean removeShiftlist(@PathParam("user_id") String user_id, @PathParam("shift_id") int shift_id){
        System.out.println("remove Shift_list");
        return dao.removeShiftlist(user_id, shift_id);
    }


    //Trenger ikke disse, man kan hente ut infoen som dette gir ut fra listene. Gir 500 error siden de prøver å hente ut samme info.
    /*
    @Path("{want_swap}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<ShiftList> getWantSwap(@PathParam("want_swap") boolean swap){
        System.out.println("getWantSwap");
        return dao.getWantSwap(swap);
    }

    //Skal være mulig å bruke update shiftlist for tilsvarende funskjon uten konflikter
    @Path("{user_id}/{shift_id}/{want_swap}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean Want_swap(ShiftList s_l) {
        System.out.println("createWantSwap");
        return dao.wantSwap(s_l);
    }
     */
}
