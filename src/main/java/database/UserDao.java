package database;

import dataclasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class UserDao {

    private final transient String errorMessage = "Could not connect to database: ";
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Adds new user to the database.
     * @param user User object to be added to database
     * @return Saved User object with new ID assigned by database
     */
    public User addUser(User user) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException(
                    "Username '" + user.getUsername() + "' is already taken");
        }
        try (PreparedStatement statement = this.dataSource.getConnection()
                .prepareStatement(
                        "INSERT INTO users(username, password, seed, email) values (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSeed());
            statement.setString(4, user.getEmail());
            int rows = statement.executeUpdate();
            if (rows > 0) {
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        user.setUserId(result.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return user;
    }

    /**
     * Checks if provided username is already taken by other user.
     * @param username Username to check
     * @return true if username is already taken, false otherwise
     */
    public boolean isUsernameTaken(String username) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "SELECT EXISTS(SELECT 1 FROM users WHERE username=?);")) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return false;
    }

    /**
     * Checks if user is authorized (exists in database).
     * @param user User object with username and password for authorization
     * @return true if user is authorized, false otherwise
     */
    public boolean isAuthorized(User user) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "SELECT EXISTS(SELECT 1 FROM users WHERE username=? AND password=?);")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves user from the database based of ID.
     * @param id ID of the user to find
     * @return User object with provided ID
     */
    public User getUserById(int id) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "SELECT user_id, username, password, seed, email FROM users WHERE user_id = ?;")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.first()) {
                    return null;
                }
                int userId = result.getInt(1);
                String username = result.getString(2);
                String password = result.getString(3);
                String seed = result.getString(4);
                String email = result.getString(5);
                return new User(userId, username, password, seed, email);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
    }

    /**
     * Changes password and seed of the user in the database.
     * @param userId ID of the user to change password
     * @param newPassword new password
     * @return User object with updated password
     */
    public User changePassword(int userId, String newPassword, String newSeed) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "UPDATE users SET password = ? WHERE user_id = ?")) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return changeSeed(userId, newSeed);
    }

    /**
     * Changes seed of the user in the database.
     * @param userId ID of the user to change password
     * @param newSeed new seed
     * @return User object with updated seed
     */
    public User changeSeed(int userId, String newSeed) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "UPDATE users SET seed = ? WHERE user_id = ?")) {
            statement.setString(1, newSeed);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return getUserById(userId);
    }

    /**
     * Deletes user from the database.
     * @param userId ID of the user to delete
     * @return true if user was successfully deleted, false otherwise
     */
    public boolean deleteUser(int userId) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "DELETE FROM users WHERE user_id = ?")) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
        return getUserById(userId) == null;
    }

    /**
     * Retrieves user from the database based on unique username.
     * @param username ID of the user to find
     * @return User object with provided username
     */
    public User getUserByUsername(String username) throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "SELECT user_id, username, password, seed, email FROM users WHERE username = ?;")) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.first()) {
                    return null;
                }
                int userId = result.getInt(1);
                String user = result.getString(2);
                String password = result.getString(3);
                String seed = result.getString(4);
                String email = result.getString(5);
                return new User(userId, user, password, seed, email);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
    }
}
