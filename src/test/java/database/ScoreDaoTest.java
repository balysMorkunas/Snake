package database;

import dataclasses.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unused")
public class ScoreDaoTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private Score score;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public void setStatement(PreparedStatement statement) {
        this.statement = statement;
    }

    public ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    /**
     * Sets up all dependencies.
     */
    @BeforeEach
    void setUp() {
        dataSource = Mockito.mock(DataSource.class);
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        result = Mockito.mock(ResultSet.class);
        score = new Score(0, 42, 100);
    }

    @Test
    void getDataSourceTest() {
        ScoreDao scoreDao = new ScoreDao(dataSource);
        Assertions.assertEquals(dataSource, scoreDao.getDataSource());
    }

    @Test
    void setDataSourceTest() {
        ScoreDao scoreDao = new ScoreDao(null);
        scoreDao.setDataSource(dataSource);
        Assertions.assertEquals(dataSource, scoreDao.getDataSource());
    }

    @Test
    void addNullScoreTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ScoreDao(dataSource).addScore(null);
        });
    }

    @Test
    void addScoreTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(statement.getGeneratedKeys()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(8);
        Score addedScore = new ScoreDao(dataSource).addScore(score);
        Assertions.assertEquals(8, addedScore.getScoreId());
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).addScore(score);
        });
    }

    @Test
    void addScoreFailTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(0);
        Score addedScore = new ScoreDao(dataSource).addScore(score);
        Assertions.assertEquals(score.getScoreId(), addedScore.getScoreId());
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).addScore(score);
        });
    }

    @Test
    void addScoreExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new ScoreDao(dataSource).addScore(score);
        });
    }

    @Test
    void getScoreByIdTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(score.getScoreId());
        Mockito.when(result.getInt(2)).thenReturn(score.getUserId());
        Mockito.when(result.getInt(3)).thenReturn(score.getScoreValue());
        Score foundScore = new ScoreDao(dataSource).getScoreById(score.getScoreId());
        Assertions.assertEquals(score, foundScore);
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).getScoreById(score.getScoreId());
        });
    }

    @Test
    void getScoreByIdNotFoundTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        Score foundScore = new ScoreDao(dataSource).getScoreById(score.getScoreId());
        Assertions.assertNull(foundScore);
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).getScoreById(score.getScoreId());
        });
    }

    @Test
    void getScoreByIdExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new ScoreDao(dataSource).getScoreById(score.getScoreId());
        });
    }

    @Test
    void deleteScoreTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        boolean deleted = new ScoreDao(dataSource).deleteScore(score.getScoreId());
        Mockito.verify(statement).executeUpdate();
        Assertions.assertTrue(deleted);
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).deleteScore(score.getScoreId());
        });
    }

    @Test
    void deleteScoreFailTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(score.getScoreId());
        Mockito.when(result.getInt(2)).thenReturn(score.getUserId());
        Mockito.when(result.getInt(3)).thenReturn(score.getScoreValue());
        boolean deleted = new ScoreDao(dataSource).deleteScore(score.getScoreId());
        Mockito.verify(statement).executeUpdate();
        Assertions.assertFalse(deleted);
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).deleteScore(score.getScoreId());
        });
    }

    @Test
    void deleteScoreExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(SQLException.class);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(score.getScoreId());
        Mockito.when(result.getInt(2)).thenReturn(score.getUserId());
        Mockito.when(result.getInt(3)).thenReturn(score.getScoreValue());
        Assertions.assertThrows(Exception.class, () -> {
            new ScoreDao(dataSource).deleteScore(score.getScoreId());
        });
        Mockito.verify(statement).executeUpdate();
    }

    @Test
    void getAllScoresForUserTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.next()).thenReturn(true, false);
        Mockito.when(result.getInt(1)).thenReturn(score.getScoreId());
        Mockito.when(result.getInt(2)).thenReturn(score.getUserId());
        Mockito.when(result.getInt(3)).thenReturn(score.getScoreValue());
        List<Score> scores = new ScoreDao(dataSource).getAllScoresForUser(score.getUserId());
        Assertions.assertEquals(Collections.singletonList(score), scores);
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).getAllScoresForUser(score.getUserId());
        });
    }

    @Test
    void getAllScoresForUserNotFoundTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        List<Score> scores = new ScoreDao(dataSource).getAllScoresForUser(score.getUserId());
        Assertions.assertEquals(0, scores.size());
        Assertions.assertDoesNotThrow(() -> {
            new ScoreDao(dataSource).getAllScoresForUser(score.getUserId());
        });
    }

    @Test
    void getAllScoresForUserExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new ScoreDao(dataSource).getAllScoresForUser(score.getUserId());
        });
    }
}
