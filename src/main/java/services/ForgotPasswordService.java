package services;

import database.UserDao;
import dataclasses.User;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

public class ForgotPasswordService {

    private transient UserDao userDao;
    private transient SendEmailService sendEmailService;

    public ForgotPasswordService(UserDao userDao, SendEmailService sendEmailService) {
        this.userDao = userDao;
        this.sendEmailService = sendEmailService;
    }

    /**
     * Generates a random 10 character long password.
     * @return Random string
     */
    @SuppressWarnings("PMD")
    public String generateRandomPass() {
        String charset = "abcdefghijklmnopqrstuvwxyz";
        charset += charset.toUpperCase();
        charset += "1234567890!@#$%&*";
        StringBuilder randomPass = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            randomPass.append(charset.charAt(random.nextInt(charset.length())));
        }
        return randomPass.toString();
    }

    /**
     * Checks if email matches, generates a new password and sends an email with it.
     * @param username User's username
     * @param email User's email
     * @throws Exception if email sending brakes down or if email was incorrect
     */
    public void forgotPassword(String username, String email) throws Exception {
        User user = this.userDao.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Username does not exist");
        }
        if (!email.equals(user.getEmail())) {
            throw new IllegalArgumentException("Incorrect email");
        }
        String newPass = this.generateRandomPass();
        String newSalt = BCrypt.gensalt();
        String hashedPass = BCrypt.hashpw(newPass, newSalt);
        this.userDao.changePassword(user.getUserId(), hashedPass, newSalt);
        sendEmailService.sendForgotPassEmail(user.getEmail(), username, newPass);
    }
}
