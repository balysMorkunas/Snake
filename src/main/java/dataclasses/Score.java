package dataclasses;

import java.util.Objects;

public class Score {
    private int scoreId;
    private int userId;
    private int scoreValue;

    /**
     * Constructor for Score class.
     * @param scoreId ID of the score
     * @param userId ID of the user to whom the score belongs
     * @param scoreValue The score
     */
    public Score(int scoreId, int userId, int scoreValue) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.scoreValue = scoreValue;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Score)) {
            return false;
        }
        Score score1 = (Score) o;
        return getScoreId() == score1.getScoreId() && getUserId() == score1.getUserId()
                && getScoreValue() == score1.getScoreValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScoreId(), getUserId(), getScoreValue());
    }

    @Override
    public String toString() {
        return "Score{scoreId=" + scoreId + ", userId=" + userId
                + ", scoreValue=" + scoreValue + '}';
    }
}
