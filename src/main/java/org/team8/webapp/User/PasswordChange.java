package org.team8.webapp.User;

/**
 * Created by espen on 26.01.2017.
 */
public class PasswordChange {
    private String oldpassword;
    private String newpassword;

    public PasswordChange(){};

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
