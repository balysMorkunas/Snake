package database;

import dataclasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unused")
public class UserDaoTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private User user;
    private static final String salt = "256";

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        user = new User(0, "test", "password", "42", "test@gmail.com");
    }

    @Test
    void getDataSourceTest() {
        UserDao userDao = new UserDao(dataSource);
        Assertions.assertEquals(dataSource, userDao.getDataSource());
    }

    @Test
    void setDataSourceTest() {
        UserDao userDao = new UserDao(null);
        userDao.setDataSource(dataSource);
        Assertions.assertEquals(dataSource, userDao.getDataSource());
    }

    @Test
    void addNullUserTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDao(dataSource).addUser(null);
        });
    }

    @Test
    void addUserWithBusyUsernameTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getBoolean(1)).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new UserDao(dataSource).addUser(user);
        });
    }

    @Test
    void addUserSuccessTest() throws Exception {
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getBoolean(1)).thenReturn(false);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(statement.getGeneratedKeys()).thenReturn(result);
        Mockito.when(result.getInt(1)).thenReturn(42);
        User addedUser = new UserDao(dataSource).addUser(user);
        Assertions.assertEquals(42, addedUser.getUserId());
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).addUser(user);
        });
    }

    @Test
    void addUserFailTest() throws Exception {
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.getBoolean(1)).thenReturn(false);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(0);
        User addedUser = new UserDao(dataSource).addUser(user);
        Assertions.assertEquals(user, addedUser);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).addUser(user);
        });
    }

    @Test
    void addUserNoResultsTest() throws Exception {
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(false);
        Mockito.when(result.getBoolean(1)).thenReturn(false);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(statement.getGeneratedKeys()).thenReturn(result);
        User addedUser = new UserDao(dataSource).addUser(user);
        Assertions.assertEquals(user, addedUser);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).addUser(user);
        });
    }

    @Test
    void addUserExceptionTest() throws Exception {
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getBoolean(1)).thenReturn(false);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),
                Mockito.eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).addUser(user);
        });
    }

    @Test
    void usernameTakenTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getBoolean(1)).thenReturn(true);
        boolean taken = new UserDao(dataSource).isUsernameTaken(user.getUsername());
        Assertions.assertTrue(taken);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).isUsernameTaken(user.getUsername());
        });
    }

    @Test
    void usernameNotTakenTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getBoolean(1)).thenReturn(false);
        boolean taken = new UserDao(dataSource).isUsernameTaken(user.getUsername());
        Assertions.assertFalse(taken);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).isUsernameTaken(user.getUsername());
        });
    }

    @Test
    void usernameNoResultsTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(false);
        Mockito.when(result.getBoolean(1)).thenReturn(true);
        boolean taken = new UserDao(dataSource).isUsernameTaken(user.getUsername());
        Assertions.assertFalse(taken);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).isUsernameTaken(user.getUsername());
        });
    }

    @Test
    void usernameTakenExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).isUsernameTaken(user.getUsername());
        });
    }

    @Test
    void authorizedUserTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(true);
        Mockito.when(result.getBoolean(1)).thenReturn(true);
        boolean authorized = new UserDao(dataSource).isAuthorized(user);
        Assertions.assertTrue(authorized);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).isAuthorized(user);
        });
    }

    @Test
    void unauthorizedUserTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.next()).thenReturn(false);
        boolean authorized = new UserDao(dataSource).isAuthorized(user);
        Assertions.assertFalse(authorized);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).isAuthorized(user);
        });
    }

    @Test
    void authorizedUserExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).isAuthorized(user);
        });
    }

    @Test
    void getUserByIdTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        User foundUser = new UserDao(dataSource).getUserById(user.getUserId());
        Assertions.assertEquals(user, foundUser);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).getUserById(user.getUserId());
        });
    }

    @Test
    void getUserByIdNotFoundTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        User foundUser = new UserDao(dataSource).getUserById(user.getUserId());
        Assertions.assertNull(foundUser);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).getUserById(user.getUserId());
        });
    }

    @Test
    void getUserByIdExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).getUserById(user.getUserId());
        });
    }

    @Test
    void changePasswordTest() throws Exception {
        String newPass = "new password";
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(newPass);
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        User changedUser = new UserDao(dataSource)
                .changePassword(user.getUserId(), newPass, "newSeed");
        Assertions.assertEquals(newPass, changedUser.getPassword());
        Mockito.verify(statement, Mockito.atLeastOnce()).executeUpdate();
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).changePassword(user.getUserId(), newPass, "newSeed");
        });
    }

    @Test
    void changePasswordFailTest() throws Exception {
        String newPass = "new password";
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        User changedUser = new UserDao(dataSource)
                .changePassword(user.getUserId(), newPass, "newseed");
        Assertions.assertEquals(user.getPassword(), changedUser.getPassword());
        Mockito.verify(statement, Mockito.atLeastOnce()).executeUpdate();
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).changePassword(user.getUserId(), newPass, "newseed");
        });
    }

    @Test
    void changePasswordExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(SQLException.class);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        String newPass = "new password";
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).changePassword(user.getUserId(), newPass, "seed");
        });
        Mockito.verify(statement, Mockito.atLeastOnce()).executeUpdate();
    }

    @Test
    void changeSeedTest() throws Exception {
        String newSeed = "256";
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(newSeed);
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        User changedUser = new UserDao(dataSource).changeSeed(user.getUserId(), newSeed);
        Assertions.assertEquals(newSeed, changedUser.getSeed());
        Mockito.verify(statement).executeUpdate();
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).changeSeed(user.getUserId(), newSeed);
        });
    }

    @Test
    void changeSeedFailTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        User changedUser = new UserDao(dataSource).changeSeed(user.getUserId(), "25");
        Assertions.assertEquals(user.getPassword(), changedUser.getPassword());
        Mockito.verify(statement).executeUpdate();
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).changeSeed(user.getUserId(), "25");
        });
    }

    @Test
    void changeSeedExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(SQLException.class);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).changeSeed(user.getUserId(), "250");
        });
        Mockito.verify(statement).executeUpdate();
    }

    @Test
    void deleteUserTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        boolean deleted = new UserDao(dataSource).deleteUser(user.getUserId());
        Assertions.assertTrue(deleted);
        Mockito.verify(statement).executeUpdate();
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).deleteUser(user.getUserId());
        });
    }

    @Test
    void deleteUserFailTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        boolean deleted = new UserDao(dataSource).deleteUser(user.getUserId());
        Assertions.assertFalse(deleted);
        Mockito.verify(statement).executeUpdate();
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).deleteUser(user.getUserId());
        });
    }

    @Test
    void deleteUserExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenThrow(SQLException.class);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).deleteUser(user.getUserId());
        });
        Mockito.verify(statement).executeUpdate();
    }

    @Test
    void getUserByUsernameTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(true);
        Mockito.when(result.getInt(1)).thenReturn(user.getUserId());
        Mockito.when(result.getString(2)).thenReturn(user.getUsername());
        Mockito.when(result.getString(3)).thenReturn(user.getPassword());
        Mockito.when(result.getString(4)).thenReturn(user.getSeed());
        Mockito.when(result.getString(5)).thenReturn(user.getEmail());
        User foundUser = new UserDao(dataSource).getUserByUsername(user.getUsername());
        Assertions.assertEquals(user, foundUser);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).getUserByUsername(user.getUsername());
        });
    }

    @Test
    void getUserByUsernameNotFoundTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(result);
        Mockito.when(result.first()).thenReturn(false);
        User foundUser = new UserDao(dataSource).getUserByUsername(user.getUsername());
        Assertions.assertNull(foundUser);
        Assertions.assertDoesNotThrow(() -> {
            new UserDao(dataSource).getUserByUsername(user.getUsername());
        });
    }

    @Test
    void getUserByUsernameExceptionTest() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);
        Assertions.assertThrows(Exception.class, () -> {
            new UserDao(dataSource).getUserByUsername(user.getUsername());
        });
    }
}
