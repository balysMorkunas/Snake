package dataclasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LeaderboardItemTest {

    private static final String username = "user";

    @Test
    public void geUsernameTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        Assertions.assertEquals("user", item.getUsername());
    }

    @Test
    public void setUsernameTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        item.setUsername("test");
        Assertions.assertEquals("test", item.getUsername());
    }

    @Test
    public void getHighestScoreTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        Assertions.assertEquals(10, item.getHighestScore());
    }

    @Test
    public void setHighestScoreTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        item.setHighestScore(20);
        Assertions.assertEquals(20, item.getHighestScore());
    }

    @Test
    public void equalsSameTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        Assertions.assertEquals(item, item);
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "PMD.EqualsNull"})
    @Test
    public void equalsNullTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        Assertions.assertFalse(item.equals(null));
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "PMD.EqualsNull",
            "EqualsBetweenInconvertibleTypes"})
    @Test
    public void equalsDifferentClassesTest() {
        LeaderboardItem item = new LeaderboardItem(username, 10);
        Assertions.assertFalse(item.equals(new Score(0, 0, 0)));
    }

    @Test
    public void equalsDifferentUsernameTest() {
        LeaderboardItem item1 = new LeaderboardItem(username, 10);
        LeaderboardItem item2 = new LeaderboardItem("test", 10);
        Assertions.assertNotEquals(item2, item1);
    }

    @Test
    public void equalsDifferentHighestScoreTest() {
        LeaderboardItem item1 = new LeaderboardItem(username, 10);
        LeaderboardItem item2 = new LeaderboardItem(username, 20);
        Assertions.assertNotEquals(item2, item1);
    }

    @Test
    public void hashCodeTest() {
        LeaderboardItem item1 = new LeaderboardItem(username, 10);
        LeaderboardItem item2 = new LeaderboardItem(username, 10);
        Assertions.assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    public void toStringTest() {
        String str = "LeaderboardItem{username'user', highestScore=10}";
        LeaderboardItem item = new LeaderboardItem(username, 10);
        Assertions.assertEquals(str, item.toString());
    }
}