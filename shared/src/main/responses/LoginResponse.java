package responses;

/**
 * This class represents the response sent from logging in a user
 */

public class LoginResponse {
    /**
     * The username of the newly logged-in user
     */
    private String username;
    /**
     * The authorization token of the newly logged-in user
     */
    private String authToken;
    /**
     * The message that appears when the request goes through
     */
    private String message;

    /**
     * Constructs a LoginResponse object that represents a successful login attempt
     * @param username The username of the newly logged-in user
     * @param authToken The authorization token of the newly logged-in user
     */
    public LoginResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
        this.message = null;
    }

    /**
     * Constructs a LoginResponse object that represents a failed login attempt
     * @param message The message that appears when the request goes through
     */
    public LoginResponse(String message) {
        this.username = null;
        this.authToken = null;
        this.message = message;
    }

    /**
     * Return the username of the newly logged-in user
     * @return The username of the newly logged-in user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the newly logged-in user
     * @param username The username of the newly logged-in user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the authorization token of the newly logged-in user
     * @return The authorization token of the newly logged-in user
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authorization token of the newly logged-in user
     * @param authToken The authorization token of the newly logged-in user
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the message that appears when the request goes through
     * @return The message that appears when the request goes through
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message that appears when the request goes through
     * @param message The message that appears when the request goes through
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
