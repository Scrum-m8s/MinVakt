package org.team8.webapp.Employee;

import java.sql.Date;

/**
 * Created by Mr.Easter on 18/01/2017.
 */
public class AvailableEmployees {
    private int shift_id;
    private Date my_date;
    private int category;

    public AvailableEmployees() {}

    public AvailableEmployees(int shift_id, Date my_date, int category) {
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

    public Date getMy_date() {return my_date;}

    public void setMy_date(Date my_date) {this.my_date = my_date;}

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {this.category = category;}
}
