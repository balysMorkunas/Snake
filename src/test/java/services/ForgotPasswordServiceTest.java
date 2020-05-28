package services;

import database.UserDao;
import dataclasses.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ForgotPasswordServiceTest {

    private transient ForgotPasswordService forgotPasswordService;
    private transient UserDao userDao;
    private transient User user;
    private transient SendEmailService sendEmailService;

    @BeforeEach
    void before() {
        this.userDao = Mockito.mock(UserDao.class);
        this.sendEmailService = Mockito.mock(SendEmailService.class);
        this.forgotPasswordService = new ForgotPasswordService(userDao, sendEmailService);
        this.user = new User(42, "test", "pass", "seed", "test@gmail.com");
    }

    @Test
    void generateRandomPassTest() {
        String randomPass1 = this.forgotPasswordService.generateRandomPass();
        String randomPass2 = this.forgotPasswordService.generateRandomPass();
        Assertions.assertNotEquals(randomPass1, randomPass2);
    }

    @Test
    void forgotPassTest() throws Exception {
        Mockito.when(userDao.getUserByUsername(user.getUsername())).thenReturn(user);
        forgotPasswordService.forgotPassword(user.getUsername(), user.getEmail());
        Mockito.verify(userDao).getUserByUsername(user.getUsername());
        Mockito.verify(userDao).changePassword(
                Mockito.eq(user.getUserId()), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(sendEmailService).sendForgotPassEmail(
                Mockito.eq(user.getEmail()), Mockito.eq(user.getUsername()), Mockito.anyString());
        Assertions.assertDoesNotThrow(() -> {
            forgotPasswordService.forgotPassword(user.getUsername(), user.getEmail());
        });
    }

    @Test
    void forgotPassFailTest() throws Exception {
        Mockito.when(userDao.getUserByUsername(user.getUsername())).thenReturn(user);
        try {
            forgotPasswordService.forgotPassword(user.getUsername(), "wrongEmail@gmail.com");
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals("Incorrect email", ex.getMessage());
        }
        Mockito.verify(userDao).getUserByUsername(user.getUsername());
        Mockito.verify(userDao, Mockito.never()).changePassword(
                Mockito.eq(user.getUserId()), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(sendEmailService, Mockito.never()).sendForgotPassEmail(
                Mockito.eq(user.getEmail()), Mockito.eq(user.getUsername()), Mockito.anyString());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            forgotPasswordService.forgotPassword(user.getUsername(), "wrongEmail@gmail.com");
        });
    }
}
