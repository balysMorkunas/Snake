package services;

import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailService {

    private transient SMTPTransport transport;

    public SendEmailService(SMTPTransport smtpTransport) {
        this.transport = smtpTransport;
    }

    public SendEmailService() {
    }

    /**
     * Sends an email with new password.
     * @param email Receiver's email
     * @param username Receiver's username
     * @param newPassword New password of receiver
     * @throws Exception if something brakes
     */
    public void sendForgotPassEmail(String email, String username, String newPassword)
            throws Exception {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.port", "25");
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("gamesnakegroup5@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            msg.setSubject("Snake Game: Forgot Password");
            msg.setText("Dear " + username + ",\n\nYour password has been reset to:\n"
                    + newPassword + "\n\nPlease make sure to change this password when you login."
                    + "\n\nBest regards,\nSnake Game team.");
            msg.setSentDate(new Date());
            if (this.transport == null) {
                this.transport = (SMTPTransport) session.getTransport("smtp");
            }
            this.transport.connect("smtp.gmail.com", "gamesnakegroup5@gmail.com", "Sn@keGame123");
            this.transport.sendMessage(msg, msg.getAllRecipients());
            this.transport.close();
        } catch (MessagingException e) {
            throw new Exception("Could not send email with new password: " + e.getMessage());
        }
    }
}
