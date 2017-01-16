package org.team8.webapp.LoginManagment;

import org.team8.webapp.User.User;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.team8.webapp.LoginManagment.LoginCheck.validateCredentials;

/**
 * Created by espen on 12.01.2017.
 */
public class MinvaktLoginModule implements LoginModule {

    public static final int ROLE_ADMIN = 0;
    public static final int ROLE_EMPLOYEE = 1;

    private CallbackHandler handler;
    private Subject subject;
    private UserPrincipal userPrincipal;
    private RolePrincipal rolePrincipal;
    private String login;
    private List<String> userGroups;

    @Override
    public void initialize(Subject subject,
                           CallbackHandler callbackHandler,
                           Map<String, ?> sharedState,
                           Map<String, ?> options){
        handler = callbackHandler;
        this.subject = subject;

    }

    @Override
    public boolean login() throws LoginException{

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);

        try{
            handler.handle(callbacks);
            String name = ((NameCallback) callbacks[0]).getName();
            String password = String.valueOf(((PasswordCallback)callbacks[1]).getPassword());

            User user = new User(name, password);

            int userRole = validateCredentials(user);

            if(userRole > -1){
                login = name;
                userGroups = new ArrayList<String>();

                if(ROLE_ADMIN == userRole){
                    userGroups.add("admin");
                }else{
                    userGroups.add("employee");
                }

                return true;
            }

            throw new LoginException("Authentification failed");

        }catch (IOException e) {
            throw new LoginException(e.getMessage());
        }catch(UnsupportedCallbackException e){
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException{

        userPrincipal = new UserPrincipal(login);
        subject.getPrincipals().add(userPrincipal);

        if(userGroups != null && userGroups.size() > 0){
            for(String groupName : userGroups){
                rolePrincipal = new RolePrincipal(groupName);
                subject.getPrincipals().add(rolePrincipal);
            }
        }
        return true;
    }

    @Override
    public boolean abort() throws LoginException{
        return false;
    }

    @Override
    public boolean logout() throws LoginException{
        subject.getPrincipals().remove(userPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        return true;
    }

}
