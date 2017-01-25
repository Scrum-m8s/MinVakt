package org.team8.webapp.ShiftList;

import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.TimeList.TimeListDAO;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mr.Easter on 20/01/2017.
 */

//Collection of functions performed with data from multiple tables from the database.
@Path("/function/")
public class ShiftListFunctionResource {
    ShiftListDAO sdao = new ShiftListDAO();
    EmployeeDAO edao = new EmployeeDAO();
    TimeListDAO tdao = new TimeListDAO();

    //Updates time_list with deviances from shift_list, and if successful, removes deviances from shift_list.
    //TODO: This needs some work, especially if we are supposed to have liquid updates.
    @GET
    @Path("updatedeviances/{year}/{month}")
    @GET
    public String updateDeviances(@PathParam("year") int year, @PathParam("month") int month) {
        System.out.println("Update Deviance in Time_list/Shift_list");
        //Fetches entire shift_list


        ArrayList<ShiftList> theShiftList = sdao.getShiftLists();
        ArrayList<ShiftList> newList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        //Makes a temporary list with shifts contained within criteria (year, month)
        for (int i=0;i<theShiftList.size();i++){
            cal.setTime(theShiftList.get(i).getMy_date());

            System.out.println("getYear: "+cal.get(Calendar.YEAR) + " year " + year + " getMonth: "+ cal.get(Calendar.MONTH) + " month: " + month);

            if (cal.get(Calendar.YEAR)==year && cal.get(Calendar.MONTH)==month){
                System.out.println(theShiftList.get(i).getUser_id()+" "+theShiftList.get(i).getMy_date());
                newList.add(theShiftList.get(i));
            }
        }

        String message="";
        boolean result = false;
        //Adds every deviance from temporary list to time_list and removes deviance from shift_list. See ShiftListDAO.
        for (int i=0;i<newList.size();i++){
            if (newList.get(i).getDeviance()!=0){
                result = tdao.updateDeviance(newList.get(i),year,month);     //Updates deviance.
                message+="\nUser "+newList.get(i).getUser_id()+" has deviance "+newList.get(i).getDeviance()+" added.";
                if (result){
                    //TODO: Evaluate necessity of deleting deviance from shift_list after adding to time_list.
                    //Removes deviance.
                    //sdao.removeDeviance(newList.get(i).getMy_date(),newList.get(i).getShift_id(),newList.get(i).getUser_id());
                }
            }
        }
        return "Deviances updated. \n" + message;
    }

    //Finds the amount of employees with specific categories registered on shifts during a parameter-given day(yyyy-MM-dd).
    @Path("getshifttotal/{my_date}")
    @GET
    public ArrayList<ShiftDay> getShiftsByDate(@PathParam("my_date") String my_date_string){
        System.out.println(my_date_string);

        //Entire shiftlist for that date fetched from database.
        ArrayList<ShiftList> shift_list = sdao.getShiftListsByDate(my_date_string);

        //Initiating resulting array after upcoming for-loop.
        ArrayList<ShiftDay> shiftsThisDay = new ArrayList<ShiftDay>();
        shiftsThisDay.add(new ShiftDay(1,0,0,0));
        shiftsThisDay.add(new ShiftDay(2,0,0,0));
        shiftsThisDay.add(new ShiftDay(3,0,0,0));

        int tempCat;
        String tempUser;

        //Goes through each entry in the shift_list.
        for (int i=0;i<shift_list.size();i++){
            tempUser = shift_list.get(i).getUser_id();
            tempCat = edao.getEmployeeById(tempUser).getCategory();
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
