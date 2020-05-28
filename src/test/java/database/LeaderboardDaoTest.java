package database;

import dataclasses.LeaderboardItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unused")
public class LeaderboardDaoTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

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

    /**
     * Sets up all dependencies.
     */
    @BeforeEach
    void setUp() {
        dataSource = Mockito.mock(DataSource.class);
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        result = Mockito.mock(ResultSet.class);
    }

    @Test
    void getDataSourceTest() {
        LeaderboardDao leaderboardDao = new LeaderboardDao(dataSource);
        Assertions.assertEquals(dataSource, leaderboardDao.getDataSource());
    }

    @Test
    void setDataSourceTest() {
        LeaderboardDao leaderboardDao = new LeaderboardDao(null);
        leaderboardDao.setDataSource(dataSource);
        Assertions.assertEquals(dataSource, leaderboardDao.getDataSource());
    }

    @Test
    void getTop10Test() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.next()).thenReturn(true, false);
        LeaderboardItem item = new LeaderboardItem("user", 10);
        Mockito.when(result.getString(1)).thenReturn(item.getUsername());
        Mockito.when(result.getInt(2)).thenReturn(item.getHighestScore());
        List<LeaderboardItem> top10 = new LeaderboardDao(dataSource).getTop10Scores();
        Assertions.assertEquals(Collections.singletonList(item), top10);
        Assertions.assertDoesNotThrow(() -> {
            new LeaderboardDao(dataSource).getTop10Scores();
        });
    }

    @Test
    void getTop10NotFoundTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        List<LeaderboardItem> top10 = new LeaderboardDao(dataSource).getTop10Scores();
        Assertions.assertEquals(0, top10.size());
        Assertions.assertDoesNotThrow(() -> {
            new LeaderboardDao(dataSource).getTop10Scores();
        });
    }

    @Test
    void getTop10ExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new LeaderboardDao(dataSource).getTop10Scores();
        });
    }
}
