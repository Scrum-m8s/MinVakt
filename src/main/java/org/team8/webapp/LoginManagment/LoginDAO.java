package org.team8.webapp.LoginManagment;

import org.team8.webapp.Database.DatabaseManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by espen on 11.01.2017.
 */
public class LoginDAO extends DatabaseManagement{
    public LoginDAO(){super();}

    Connection conn = null;
    PreparedStatement prep = null;
    ResultSet res = null;

    public boolean validateLogin(String username, String password){

        String databaseUsername = null;
        String databasePassword = null;

        String query = "SELECT * FROM User WHERE user_id = ?";

        if(setUp()){
            try{
                conn = getConnection();
                prep = conn.prepareStatement(query);
                res = prep.executeQuery();

                while(res.next()){
                    databaseUsername = res.getString(1);
                    databasePassword = res.getString(2);
                }
            }catch (SQLException e){
                System.err.println("Error getting login data from database");
                return false;
            }finally{
                finallyStatement(res, prep);
            }
        }

        if(databaseUsername == null){
            return false;
        }else{
            // TODO: skill ut salt, hash passord med salt og sjekk mot passord fra database

            return true;
        }
    }
}
