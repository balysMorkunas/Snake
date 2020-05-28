package dataclasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    private static final String username = "user";
    private static final String password = "pass";

    @Test
    public void getUserIdTest() {
        User user = new User(42, username, password, "1024", "test@gmail.com");
        Assertions.assertEquals(42, user.getUserId());
    }

    @Test
    public void setUserIdTest() {
        User user = new User(42, username, password, "1025", "test@gmail.com");
        user.setUserId(8);
        Assertions.assertEquals(8, user.getUserId());
    }

    @Test
    public void getUsernameTest() {
        User user = new User(42, username, password, "1026", "test1@gmail.com");
        Assertions.assertEquals("user", user.getUsername());
    }

    @Test
    public void setUsernameTest() {
        User user = new User(42, username, password, "1027", "test1@gmail.com");
        user.setUsername("test");
        Assertions.assertEquals("test", user.getUsername());
    }

    @Test
    public void getPasswordTest() {
        User user = new User(42, username, password, "1028", "test2@gmail.com");
        Assertions.assertEquals("pass", user.getPassword());
    }

    @Test
    public void setPasswordTest() {
        User user = new User(42, username, password, "1029", "test2@gmail.com");
        user.setPassword("secret");
        Assertions.assertEquals("secret", user.getPassword());
    }

    @Test
    public void getSeedTest() {
        User user = new User(42, username, password, "1020", "test3@gmail.com");
        Assertions.assertEquals("1020", user.getSeed());
    }

    @Test
    public void setSeedTest() {
        User user = new User(42, username, password, "1021", "test3@gmail.com");
        user.setSeed("2048");
        Assertions.assertEquals("2048", user.getSeed());
    }

    @Test
    public void getEmailTest() {
        User user = new User(42, username, password, "1020", "test4@gmail.com");
        Assertions.assertEquals("test4@gmail.com", user.getEmail());
    }

    @Test
    public void setEmailTest() {
        User user = new User(42, username, password, "1021", "test5@gmail.com");
        user.setEmail("user@gmail.com");
        Assertions.assertEquals("user@gmail.com", user.getEmail());
    }

    @Test
    public void equalsSameTest() {
        User user = new User(42, username, password, "1022", "test6@gmail.com");
        Assertions.assertEquals(user, user);
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "PMD.EqualsNull"})
    @Test
    public void equalsNullTest() {
        User user = new User(42, username, password, "1023", "test6@gmail.com");
        Assertions.assertFalse(user.equals(null));
    }

    @Test
    public void equalsDifferentClassesTest() {
        User user = new User(42, username, password, "124", "test7@gmail.com");
        Assertions.assertNotEquals(new Score(0, 0, 0), user);
    }

    @Test
    public void equalsDifferentIdTest() {
        User user1 = new User(42, username, password, "104", "test8@gmail.com");
        User user2 = new User(8, username, password, "104", "test8@gmail.com");
        Assertions.assertNotEquals(user2, user1);
    }

    @Test
    public void equalsDifferentUsernameTest() {
        User user1 = new User(42, username, password, "10", "test9@gmail.com");
        User user2 = new User(42, "test", password, "10", "test9@gmail.com");
        Assertions.assertNotEquals(user2, user1);
    }

    @Test
    public void equalsDifferentPasswordTest() {
        User user1 = new User(42, username, password, "14", "test0@gmail.com");
        User user2 = new User(42, username, "secret", "14", "test0@gmail.com");
        Assertions.assertNotEquals(user2, user1);
    }

    @Test
    public void equalsDifferentSeedTest() {
        User user1 = new User(42, username, password, "2", "tes@gmail.com");
        User user2 = new User(42, username, password, "1", "tes@gmail.com");
        Assertions.assertNotEquals(user2, user1);
    }

    @Test
    public void equalsDifferentEmailTest() {
        User user1 = new User(42, username, password, "2", "te@gmail.com");
        User user2 = new User(42, username, password, "2", "us@gmail.com");
        Assertions.assertNotEquals(user2, user1);
    }

    @Test
    public void hashCodeTest() {
        User user1 = new User(42, username, password, "3", "tet@gmail.com");
        User user2 = new User(42, username, password, "3", "tet@gmail.com");
        Assertions.assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void toStringTest() {
        String str = "User{userId=42, username='user', "
                + "password='pass', seed='8', email='test@gmail.com'}";
        User user = new User(42, username, password, "8", "test@gmail.com");
        Assertions.assertEquals(str, user.toString());
    }
}