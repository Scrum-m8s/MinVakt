package org.team8.webapp.EmailService;

import org.team8.webapp.Employee.Employee;
import org.team8.webapp.Employee.EmployeeDAO;
import org.team8.webapp.User.User;
import org.team8.webapp.User.UserDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.team8.webapp.LoginManagment.Hash.createHashedPassword;
import static org.team8.webapp.EmailService.SendMail.sendMail;
import static org.team8.webapp.LoginManagment.GenTemporaryPassword.generateTemporaryPassword;

/**
 * Created by espen on 24.01.2017.
 */

@Path("/mail")
public class MailServices {

    UserDAO userDAO = new UserDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @Path("reset/{userid}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String resetPassword(@PathParam("userid") String user_id){

        System.out.println("resetPassword: " + user_id);

        User user = userDAO.getUserById(user_id);
        Employee employee = employeeDAO.getEmployeeById(user_id);

        if(user == null){
            return "Bruker finnes ikke";
        }

        String newPassword = generateTemporaryPassword();
        String userMail = employee.getEmail();

        sendMail("MinVakt", userMail, "Midlertidig passord", "Ditt midlertidige passord er: " + newPassword);

        String hashedPassword = createHashedPassword(newPassword);

        user.setPassword(hashedPassword);
        userDAO.updateUser(user);

        return "Nytt passord sendt";

    }

}