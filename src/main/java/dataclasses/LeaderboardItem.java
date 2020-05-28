package dataclasses;

import java.util.Objects;

public class LeaderboardItem {

    private String username;
    private int highestScore;

    public LeaderboardItem(String username, int highestScore) {
        this.username = username;
        this.highestScore = highestScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof LeaderboardItem)) {
            return false;
        }
        LeaderboardItem that = (LeaderboardItem) o;
        return getHighestScore() == that.getHighestScore()
                && Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getHighestScore());
    }

    @Override
    public String toString() {
        return "LeaderboardItem{username'" + username + "', highestScore=" + highestScore + "}";
    }
}
