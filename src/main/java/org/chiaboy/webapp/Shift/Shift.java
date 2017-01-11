package org.chiaboy.webapp.Shift;

import java.sql.Date;

/**
 * Created by asdfLaptop on 11.01.2017.
 */
public class Shift {
    private int shift_id;
    private int hours;
    private int start_time;
    private int end_time;
    private String user_id;
    private boolean on_duty;
    private Date my_date;
    private int deviance;

    public int getShiftId() {
        return shift_id;
    }

    public void setShiftId(int shift_id) {
        this.shift_id = shift_id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getStartTime() {
        return start_time;
    }

    public void setStartTime(int start_time) {
        this.start_time = start_time;
    }

    public int getEndTime() {
        return end_time;
    }

    public void setEndTime(int end_time) {
        this.end_time = end_time;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public boolean isOnDuty() {
        return on_duty;
    }

    public void setOnDuty(boolean on_duty) {
        this.on_duty = on_duty;
    }

    public Date getMyDate() {
        return my_date;
    }

    public void setMyDate(Date my_date) {
        this.my_date = my_date;
    }

    public int getDeviance() {
        return deviance;
    }

    public void setDeviance(int deviance) {
        this.deviance = deviance;
    }
}
