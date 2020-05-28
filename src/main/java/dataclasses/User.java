package dataclasses;

import java.util.Objects;

public class User {
    private int userId;
    private String username;
    private String password;
    private String seed;
    private String email;

    /**
     * Constructor for User class.
     * @param userId ID of the user
     * @param username Username of the user
     * @param password Password of the user
     */
    public User(int userId, String username, String password, String seed, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.seed = seed;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getUserId() == user.getUserId() && Objects.equals(getUsername(), user.getUsername())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getSeed(), user.getSeed())
                && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword(), getSeed(), getEmail());
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username='" + username + '\''
                + ", password='" + password + '\'' + ", seed='" + seed + "'"
                + ", email='" + email + "'}";
    }
}
