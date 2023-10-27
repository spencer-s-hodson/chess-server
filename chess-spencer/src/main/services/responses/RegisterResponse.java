package services.responses;

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
    private String authToken;
    /**
     * The message displayed when registration fails
     */
    private String message;

    /**
     * Constructs a RegisterResponse object that represents successful registration
     * @param username The username of the newly registered user
     * @param authToken The authorization token of the newly registered user
     */
    public RegisterResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
        this.message = null;
    }

    /**
     * Constructs a RegisterResponse object that represents a failed registration
     * @param message The message displayed when registration fails
     */
    public RegisterResponse(String message) {
        this.username = null;
        this.authToken = null;
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
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authorization token of the newly registered user
     * @param authToken The authorization token of the newly registered user
     */
    public void setAuthToken(String authToken) {
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
