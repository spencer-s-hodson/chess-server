package models;

import java.util.Objects;
import java.util.UUID;

/**
 * This class contains information about an authorization token for a user
 */
public class AuthToken {

    /**
     * The authorization token for the AuthToken
     */
    private final String authToken;

    /**
     * The username for the authorization token
     */
    private String username;

    /**
     * Constructs an AuthToken object
     * @param username username
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    /**
     * Returns the authorization code
     * @return the authorization code
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Returns the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the new username
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authCode='" + authToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(this.authToken, authToken.authToken) && Objects.equals(username, authToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, username);
    }
}
