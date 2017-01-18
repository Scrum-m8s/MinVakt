package org.team8.webapp.Busy;
import java.sql.Date;
/**
 * Created by Nina on 12.01.2017.
 * Edited by Mr_Easter on 13.01.2017
 */

public class Busy {
    private String user_id;
    private int shift_id;
    private Date my_date;

    public Busy() {}

    public Busy(String user_id, int shift_id, Date my_date) {
        this.user_id = user_id;
        this.shift_id = shift_id;
        this.my_date = my_date;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public int getShiftId(){
        return shift_id;
    }

    public void setShiftId(int shift_id){
        this.shift_id = shift_id;
    }

    public Date getMyDate(){
        return my_date;
    }

    public void setMyDate(Date my_date){
        this.my_date = my_date;
    }
}
