package models;

import java.util.Objects;

/**
 * This class contains information about a user of the chess server
 */
public class User {

    /**
     * The username of the user
     */
    private String username;

    /**
     * The password of the user
     */
    private String password;

    /**
     * The email of the user
     */
    private String email;

    /**
     * Constructs a User object
     * @param username The username of the user
     * @param password The password of the user
     * @param email The email of the user
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets a new username for the user
     * @param username The new username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new password for the user
     * @param password The new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email of the user
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email for the user
     * @param email The new email for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }
}
