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

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<TimeList> getTimeListById(@PathParam("id") String id) {
        System.out.println("getTimeListsById");
        return dao.getTimeListsById(id);
    }

    @Path("{id}/{month}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public TimeList getSingleTimeList(@PathParam("id") String id, @PathParam("month") String month) {
        System.out.println("getSingleTimeList");
        return dao.getSingleTimeList(id, month);
    }

    @Path("{id}/{month}/exists")
    @GET
    //@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean rowExists(@PathParam("id") String id, @PathParam("month") String month) {
        System.out.println("rowExists: " + month + id);
        return dao.rowExists(id, month);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean createTimeList(TimeList e) {
        System.out.println("createTimeList");
        return dao.createTimeList(e);
    }

    @Path("{id}/{month}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean updateTimeList(TimeList e) {
        System.out.println("updateTimeList");
        return dao.updateTimeList(e);
    }

    @Path("{month}/{id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void removeTimeList(@PathParam("id") String id,@PathParam("month") String month) {
        dao.removeTimeList(id, month);
    }
}
