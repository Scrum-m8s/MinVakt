package org.team8.webapp.User;

/**
 *
 * @author Mr.Easter
 */

public class User {
    private String user_id;
    private String password;
    private int role;

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRole() {
        return role;
    }

    public void setUserRole(int role) {
        this.role = role;
    }
}
