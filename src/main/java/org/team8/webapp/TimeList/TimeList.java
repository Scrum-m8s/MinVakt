package org.team8.webapp.TimeList;
/**
 *
 * @author Mr.Easter
 */
public class TimeList {
    private String user_id;
    private int year;
    private int month;
    private int ordinary;
    private int overtime;
    private int absence;

    public TimeList() {}

    public TimeList(String user_id, int year, int month, int ordinary, int overtime, int absence) {
        this.user_id = user_id;
        this.year = year;
        this.month = month;
        this.ordinary = ordinary;
        this.overtime = overtime;
        this.absence = absence;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getOrdinary() {
        return ordinary;
    }

    public void setOrdinary(int ordinary) {
        this.ordinary = ordinary;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }
}
