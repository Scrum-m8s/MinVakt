package org.team8.webapp.Employee;

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

    public Employee() {}

    public Employee(String user_id, String firstname, String surname, String email, String phone_number, int category) {
        this.user_id = user_id;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.category = category;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
