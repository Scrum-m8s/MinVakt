package org.team8.webapp.Employee;

import java.sql.Date;

/**
 * Created by Mr.Easter on 18/01/2017.
 */
public class AvailableEmployee {
    private int shift_id;
    private String my_date;
    private int category;

    public AvailableEmployee() {}

    public AvailableEmployee(int shift_id, String my_date, int category) {
        this.shift_id = shift_id;
        this.my_date = my_date;
        this.category = category;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public String getMy_date() {return my_date;}

    public void setMy_date(String my_date) {this.my_date = my_date;}

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {this.category = category;}
}
