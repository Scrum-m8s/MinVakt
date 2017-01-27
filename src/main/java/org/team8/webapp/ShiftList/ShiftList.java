package org.team8.webapp.ShiftList;

import org.team8.webapp.Busy.BusyDAO;
import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Date;
/**
 * Created by mariyashchekanenko on 12/01/2017.
 * Edited by Mr_Easter on 12/01/2017.
 */
public class ShiftList extends BusyDAO {
    private String user_id;
    private int shift_id;
    private boolean on_duty;
    private Date my_date;
    private int deviance;
    private boolean want_swap;

    public ShiftList() {}

    public ShiftList(String user_id, int shift_id, boolean on_duty, Date my_date, int deviance, boolean want_swap) {
        this.user_id = user_id;
        this.shift_id = shift_id;
        this.on_duty = on_duty;
        this.my_date = my_date;
        this.deviance = deviance;
        this.want_swap = want_swap;
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

    public int getDeviance() {
        return deviance;
    }

    public void setDeviance(int deviance) {
        this.deviance = deviance;
    }

    public boolean isWant_swap() {
        return want_swap;
    }

    public void setWant_swap(boolean want_swap) {
        this.want_swap = want_swap;
    }
}