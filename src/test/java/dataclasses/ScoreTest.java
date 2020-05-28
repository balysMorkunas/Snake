package dataclasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    @Test
    public void getScoreIdTest() {
        Score score = new Score(8, 42, 256);
        Assertions.assertEquals(8, score.getScoreId());
    }

    @Test
    public void setScoreIdTest() {
        Score score = new Score(8, 42, 256);
        score.setScoreId(1);
        Assertions.assertEquals(1, score.getScoreId());
    }

    @Test
    public void getUserIdTest() {
        Score score = new Score(8, 42, 256);
        Assertions.assertEquals(42, score.getUserId());
    }

    @Test
    public void setUserIdTest() {
        Score score = new Score(8, 42, 256);
        score.setUserId(64);
        Assertions.assertEquals(64, score.getUserId());
    }

    @Test
    public void getScoreValueTest() {
        Score score = new Score(8, 42, 256);
        Assertions.assertEquals(256, score.getScoreValue());
    }

    @Test
    public void setScoreValueTest() {
        Score score = new Score(8, 42, 256);
        score.setScoreValue(512);
        Assertions.assertEquals(512, score.getScoreValue());
    }

    @Test
    public void equalsSameTest() {
        Score score = new Score(8, 42, 256);
        Assertions.assertEquals(score, score);
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "PMD.EqualsNull"})
    @Test
    public void equalsNullTest() {
        Score score = new Score(8, 42, 256);
        Assertions.assertFalse(score.equals(null));
    }

    @Test
    public void equalsDifferentClassesTest() {
        Score score = new Score(8, 42, 256);
        User user = new User(42, "user", "pass", "1024", "test@gmail.com");
        Assertions.assertNotEquals(user, score);
    }

    @Test
    public void equalsDifferentScoreIdTest() {
        Score score1 = new Score(8, 42, 256);
        Score score2 = new Score(32, 42, 256);
        Assertions.assertNotEquals(score2, score1);
    }

    @Test
    public void equalsDifferentUserIdTest() {
        Score score1 = new Score(8, 42, 256);
        Score score2 = new Score(8, 64, 256);
        Assertions.assertNotEquals(score2, score1);
    }

    @Test
    public void equalsDifferentScoreValueTest() {
        Score score1 = new Score(8, 42, 256);
        Score score2 = new Score(8, 42, 1024);
        Assertions.assertNotEquals(score2, score1);
    }

    @Test
    public void hashCodeTest() {
        Score score1 = new Score(8, 42, 256);
        Score score2 = new Score(8, 42, 256);
        Assertions.assertEquals(score1.hashCode(), score2.hashCode());
    }

    @Test
    public void toStringTest() {
        String str = "Score{scoreId=8, userId=42, scoreValue=256}";
        Score score = new Score(8, 42, 256);
        Assertions.assertEquals(str, score.toString());
    }
}