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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public Date getMy_date() {
        return my_date;
    }

    public void setMy_date(Date my_date) {
        this.my_date = my_date;
    }
}
