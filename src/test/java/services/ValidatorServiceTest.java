package services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorServiceTest {

    private final transient String weakPassMessage = "Password must be at least 8 characters long"
            + ", contain 1 lower case letter, 1 upper"
            + " case letter, one special character.";

    private transient ValidatorService validator;

    @BeforeEach
    void setupEnv() {
        validator = new ValidatorService();
    }

    @Test
    void validEmailTest() {
        String validEmail = "test@gmail.com";
        Assertions.assertTrue(validator.isValidEmail(validEmail));
    }

    @Test
    void invalidEmailTest() {
        String invalidEmail = "testgmailcom";
        Assertions.assertFalse(validator.isValidEmail(invalidEmail));
    }

    @Test
    void passwordTest() {
        String properPass = "P@ssw0rd123";
        String correctResponse = "Password is valid";
        Assertions.assertEquals(correctResponse,
                validator.passwordValidity(properPass, properPass));
    }

    @Test
    void passwordMismatchTest() {
        String properPass = "P@ssw0rd123";
        String correctResponse = "Passwords do not match!";
        Assertions.assertEquals(correctResponse,
                validator.passwordValidity(properPass, "DifferentP@ss321"));
    }

    @Test
    void passwordNoSymbolsTest() {
        String properPass = "Passw0rd123";
        Assertions.assertEquals(weakPassMessage,
                validator.passwordValidity(properPass, properPass));
    }

    @Test
    void passwordNoUppercaseTest() {
        String properPass = "p@ssw0rd123";
        Assertions.assertEquals(weakPassMessage,
                validator.passwordValidity(properPass, properPass));
    }

    @Test
    void passwordNoLowercaseTest() {
        String properPass = "P@SSW0RD123";
        Assertions.assertEquals(weakPassMessage,
                validator.passwordValidity(properPass, properPass));
    }

    @Test
    void passwordTooShortTest() {
        String properPass = "P@ss123";
        Assertions.assertEquals(weakPassMessage,
                validator.passwordValidity(properPass, properPass));
    }
}
