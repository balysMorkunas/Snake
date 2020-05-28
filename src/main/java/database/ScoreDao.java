package database;

import dataclasses.Score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ScoreDao {

    private final transient String errorMessage = "Could not connect to database: ";
    private DataSource dataSource;

    public ScoreDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Adds new score to the database.
     * @param score Score object to add to database
     * @return Saved Score object with new ID assigned by database
     */
    public Score addScore(Score score) throws Exception {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        try (PreparedStatement statement = this.dataSource.getConnection()
                     .prepareStatement("INSERT INTO scores(user_id, score) VALUES(?, ?);",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, score.getUserId());
            statement.setInt(2, score.getScoreValue());
            int rows = statement.executeUpdate();
            if (rows > 0) {
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        score.setScoreId(result.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return score;
    }

    /**
     * Retrieves a score with certain ID from the database.
     * @param scoreId ID of the score to find
     * @return Score object
     */
    public Score getScoreById(int scoreId) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                     "SELECT score_id, user_id, score FROM scores WHERE score_id = ?;")) {
            statement.setInt(1, scoreId);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.first()) {
                    return null;
                }
                int id = result.getInt(1);
                int userId = result.getInt(2);
                int score = result.getInt(3);
                return new Score(id, userId, score);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
    }

    /**
     * Deletes a score from the database.
     * @param scoreId ID of the score to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteScore(int scoreId) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "DELETE FROM scores WHERE score_id = ?")) {
            statement.setInt(1, scoreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return getScoreById(scoreId) == null;
    }

    /**
     * Retrieves all the scores from the database that belong to one user.
     * @param userId ID of the user to whom scores belong
     * @return List of scores
     */
    public List<Score> getAllScoresForUser(int userId) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "SELECT score_id, user_id, score FROM scores "
                        + "WHERE user_id = ? ORDER BY score DESC;")) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.first()) {
                    return new ArrayList<>();
                }
                List<Score> scores = new ArrayList<>();
                while (result.next()) {
                    int id = result.getInt(1);
                    int user = result.getInt(2);
                    int score = result.getInt(3);
                    scores.add(new Score(id, user, score));
                }
                return scores;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
    }
}
