package org.team8.webapp.Shift;

import java.sql.Date;

/**
 * Created by asdfLaptop on 11.01.2017.
 */
public class Shift {
    private int shift_id;
    private int hours;
    private int start_time;
    private int end_time;

    public Shift(){}

    public Shift(int shift_id, int hours, int start_time, int end_time) {
        this.shift_id = shift_id;
        this.hours = hours;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public Shift(){}

    public Shift(int shift_id, int hours, int start_time, int end_time, String user_id, boolean on_duty, Date my_date, int deviance) {
        this.shift_id = shift_id;
        this.hours = hours;
        this.start_time = start_time;
        this.end_time = end_time;
        this.user_id = user_id;
        this.on_duty = on_duty;
        this.my_date = my_date;
        this.deviance = deviance;
    }

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
}
