package org.team8.webapp.TimeList;
/**
 *
 * @author Mr.Easter
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/timelists")
public class TimeListResource {
    TimeListDAO dao = new TimeListDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<TimeList> getTimeLists() {
        System.out.println("getTimeLists");
        return dao.getTimeLists();
    }

    @Path("{month}/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public TimeList getTimeList(@PathParam("id") String id, @PathParam("month") String month) {
        System.out.println("getTimeListById: " + month + id);
        return dao.getTimeListByIdAndMonth(id, month);
    }

    @Path("{month}/{id}/exists")
    @GET
    //@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean rowExists(@PathParam("id") String id, @PathParam("month") String month) {
        System.out.println("rowExists: " + month + id);
        return dao.rowExists(id, month);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createTimeList(TimeList e) {
        System.out.println("createTimeList");
        return dao.createTimeList(e);
    }

    @Path("{month}/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateTimeList(TimeList e) {
        System.out.println("updateTimeList");
        return dao.updateTimeList(e);
    }

    @Path("{month}/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeTimeList(@PathParam("id") String id,@PathParam("month") String month) {
        dao.removeTimeList(id, month);
    }
}
