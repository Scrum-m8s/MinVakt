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

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }
}
