package org.team8.webapp.ShiftList;

import java.sql.Date;

/**
 * Created by Nina on 27.01.2017.
 */
public class Shift_list_w_cat {
    private String user_id;
    private int shift_id;
    private boolean on_duty;
    private Date my_date;
    private int category;

    public Shift_list_w_cat() {
    }

    public Shift_list_w_cat(String user_id, int shift_id, boolean on_duty, Date my_date, int category) {
        this.user_id = user_id;
        this.shift_id = shift_id;
        this.on_duty = on_duty;
        this.my_date = my_date;
        this.category = category;

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

    public boolean isOn_duty() {
        return on_duty;
    }

    public void setOn_duty(boolean on_duty) {
        this.on_duty = on_duty;
    }

    public Date getMy_date() {
        return my_date;
    }

    public void setMy_date(Date my_date) {
        this.my_date = my_date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}