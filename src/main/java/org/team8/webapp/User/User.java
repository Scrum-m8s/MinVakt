package org.team8.webapp.User;

/**
 *
 * @author Mr.Easter
 */

public class User {
    private String user_id;
    private String password;
    private int role;

    public User(){}

    public User(String user_id, String password, int role) {
        this.user_id = user_id;
        this.password = password;
        this.role = role;
    }

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
