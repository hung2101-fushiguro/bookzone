package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailUtil {

    public void sendMail(String to, String subject, String messageText) throws MessagingException {
        final String fromEmail = "quyen123456789101112@gmail.com";
        final String password = "cchk ctwo gnpk xpjh";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setText(messageText);
        System.out.println(msg);
        Transport.send(msg);
    }
}
