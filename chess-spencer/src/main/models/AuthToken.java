package models;

import java.util.Objects;

/**
 * This class contains information about an authorization token
 */
public class AuthToken {
    /**
     * The authorization code for the AuthToken
     */
    private String authCode;
    /**
     * The username for the authorization token
     */
    private String username;

    /**
     * Constructs an AuthToken object
     * @param authCode authorization code
     * @param username username
     */
    public AuthToken(String authCode, String username) {
        this.authCode = authCode;
        this.username = username;
    }

    /**
     * Returns the authorization code
     * @return the authorization code
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * Sets a new authorization code
     * @param authCode the new authorization code
     */
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
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
                "authCode='" + authCode + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authCode, authToken.authCode) && Objects.equals(username, authToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authCode, username);
    }
}
