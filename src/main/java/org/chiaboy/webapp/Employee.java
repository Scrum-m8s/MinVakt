package org.chiaboy.webapp;

/**
 * Created by asdfLaptop on 10.01.2017.
 */
public class Employee {
    private String user_id;
    private String firstname;
    private String surname;
    private String email;
    private String phone_number;
    private int category;

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
