package org.team8.webapp.ShiftList;

import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.TimeList.TimeListDAO;

import javax.ws.rs.*;
import java.util.ArrayList;

/**
 * Created by Mr.Easter on 20/01/2017.
 */

//Collection of functions performed with data from shift_list and other tables from the database.
@Path("/shiftlistfunction/")
public class ShiftListFunctionResource {
    ShiftListDAO sdao = new ShiftListDAO();
    EmployeeDAO edao = new EmployeeDAO();
    TimeListDAO tdao = new TimeListDAO();

    //Updates time_list with deviances from shift_list, and if successful, removes deviances from shift_list.
    @Path("updatedeviances/{year}/{month}")
    @POST
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    //@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String updateDeviances(@PathParam("year") int year, @PathParam("month") int month) {
        System.out.println("Update Deviance in Time_list/Shift_list");
        //Fetches entire shift_list
        ArrayList<ShiftList> listen = sdao.getShiftLists();
        String message="";
        boolean result = false;
        //Adds every deviance to time_list and removes deviance from shift_list. See ShiftListDAO.
        for (int i=0;i<listen.size();i++){
            if (listen.get(i).getDeviance()!=0){
                result = tdao.updateDeviance(listen.get(i),year,month);
                message+="User "+listen.get(i).getUser_id()+" has deviance "+listen.get(i).getDeviance()+" added.\n";
                if (result){
                    sdao.removeDeviance(listen.get(i).getUser_id(),listen.get(i).getShift_id());}
            }
        }
        return "Deviances updated. \n" + message;
    }

    //Finds the amount of employees with specific categories registered on shifts during a parameter-given day.
    @Path("getshifttotal/{my_date}")
    @GET
    public ArrayList<ShiftDay> getShiftsByDate(@PathParam("my_date") String my_date_string){
        System.out.println(my_date_string);

        //Entire shiftlist fetched from database.
        ArrayList<ShiftList> shift_list = sdao.getShiftListsByDate(my_date_string);

        //Initiating resulting array after upcoming for-loop.
        ArrayList<ShiftDay> shiftsThisDay = new ArrayList<ShiftDay>();
        shiftsThisDay.add(new ShiftDay(1,0,0,0));
        shiftsThisDay.add(new ShiftDay(2,0,0,0));
        shiftsThisDay.add(new ShiftDay(3,0,0,0));
        int tempCat;
        //Goes through each entry in the shift_list.
        for (int i=0;i<shift_list.size();i++){
            tempCat = edao.getEmployeeById(shift_list.get(i).getUser_id()).getCategory();
            //Increments values in shiftsThisDay based on shift_id and category.
            //TODO: Messy coding. Cleanup? -ASP
            if (tempCat==1){shiftsThisDay.get(shift_list.get(i).getShift_id()-1).setCategory_1(shiftsThisDay.get(shift_list.get(i).getShift_id()-1).getCategory_1()+1);}
            else if (tempCat==2){shiftsThisDay.get(shift_list.get(i).getShift_id()-1).setCategory_2(shiftsThisDay.get(shift_list.get(i).getShift_id()-1).getCategory_2()+1);}
            else if (tempCat==3){shiftsThisDay.get(shift_list.get(i).getShift_id()-1).setCategory_3(shiftsThisDay.get(shift_list.get(i).getShift_id()-1).getCategory_3()+1);}
            else {System.out.println("Error: Category out of bounds.");}
        }
    return shiftsThisDay;
    }
}
