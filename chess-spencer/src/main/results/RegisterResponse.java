package results;

import models.AuthToken;

/**
 * This class represents the response from registering a new user
 */
public class RegisterResponse {
    /**
     * The username of the newly registered user
     */
    private String username;
    /**
     * The authorization token of the newly registered user
     */
    private AuthToken authToken;
    /**
     * The message displayed when registration fails
     */
    private String message;

    /**
     * Constructs a RegisterResponse object that represents successful registration
     * @param username The username of the newly registered user
     * @param authToken The authorization token of the newly registered user
     */
    public RegisterResponse(String username, AuthToken authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    /**
     * Constructs a RegisterResponse object that represents a failed registration
     * @param message The message displayed when registration fails
     */
    public RegisterResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the username of the newly registered user
     * @return The username of the newly registered user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the newly registered user
     * @param username The username of the newly registered user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the authorization token of the newly registered user
     * @return The authorization token of the newly registered user
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authorization token of the newly registered user
     * @param authToken The authorization token of the newly registered user
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the message displayed when registration fails
     * @return The message displayed when registration fails
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message displayed when registration fails
     * @param message The message displayed when registration fails
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
