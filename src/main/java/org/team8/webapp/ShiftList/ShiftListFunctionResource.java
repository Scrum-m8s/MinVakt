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

    @Path("getshifttotal/{my_date}/{shift_id}")
    @GET
    public ArrayList<ShiftList> getShiftsByDateAndShiftId(@PathParam("my_date") String my_date_string, @PathParam("shift_id") int shift_id){
        System.out.println("getShiftsByDateAndShiftId");
        return sdao.getShiftListsByDateAndShiftId(my_date_string, shift_id);
    }
}