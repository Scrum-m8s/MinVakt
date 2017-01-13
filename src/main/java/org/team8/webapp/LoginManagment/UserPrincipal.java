package org.team8.webapp.LoginManagment;

import java.security.Principal;

/**
 * Created by espen on 12.01.2017.
 */
public class UserPrincipal implements Principal {
    private String name;

    public UserPrincipal(String name){
        super();
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }
}
