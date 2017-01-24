package org.team8.webapp.ShiftList;

import java.sql.Date;

/**
 * Created by Mr.Easter on 20/01/2017.
 */
public class ShiftDay {
    private int shift_id;
    private int category_1;
    private int category_2;
    private int category_3;

    public ShiftDay() {}

    public ShiftDay(int shift_id, int category_1, int category_2, int category_3) {

        this.shift_id = shift_id;
        this.category_1 = category_1;
        this.category_1 = category_2;
        this.category_1 = category_3;

    }
    public int getShift_id() {
        return shift_id;
    }
    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public int getCategory_1() {
        return category_1;
    }
    public void setCategory_1(int category_1) {
        this.category_1 = category_1;
    }

    public int getCategory_2() {
        return category_2;
    }
    public void setCategory_2(int category_2) {
        this.category_2 = category_2;
    }

    public int getCategory_3() {
        return category_3;
    }
    public void setCategory_3(int category_3) {
        this.category_3 = category_3;
    }



}
