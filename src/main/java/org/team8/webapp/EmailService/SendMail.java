package org.team8.webapp.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by espen on 20.01.2017.
 */
public class SendMail {
    public static void sendMail(String from, String to, String subject, String body) {

        final String username = "greenbraaaaain@gmail.com";
        final String password = "ihaveagreenbrain";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("sendMail: Mail sent to:  " + to);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args){
        sendMail("meg", "greenbraaaaain@gmail.com", "test", "Dette er en testmail");
    }

}
