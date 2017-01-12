package org.team8.webapp.Busy;

/**
 * Created by Nina on 12.01.2017.
 */

import java.sql.Date;
public class Busy {
    private String user_id;
    private int shiftID;
    private Date my_date;

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public int getShiftId(){
        return shiftID;
    }

    public void setShift_Id(int shiftID){
        this.shiftID = shiftID;
    }

    public Date getMyDate(){
        return my_date;
    }

    public void setMyDate(Date my_date){
        this.my_date = my_date;
    }
}
