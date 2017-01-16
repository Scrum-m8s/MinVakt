package org.team8.webapp.TimeList;
/**
 *
 * @author Mr.Easter
 */
public class TimeList {
    private String user_id;
    private String month;
    private int ordinary;
    private int overtime;
    private int absence;

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
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
