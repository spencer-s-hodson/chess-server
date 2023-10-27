package services.requests;

/**
 * This class represents the request to register a new user.
 */
public class RegisterRequest {
    /**
     * The username of the user to be registered
     */
    private String username;
    /**
     * The password of the user to be registered
     */
    private String password;
    /**
     * The email of the user to be registered
     */
    private String email;

    /**
     * Constructs a RegisterRequest object
     * @param username The username of the user to be registered
     * @param password The password of the user to be registered
     * @param email The email of the user to be registered
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the username of the user to be registered
     * @return The of the user to be registered
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user to be registered
     * @param username The username of the user to be registered
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user to be registered
     * @return The password of the user to be registered
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user to be registered
     * @param password The password of the user to be registered
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email of the user to be registered
     * @return The email of the user to be registered
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user to be registered
     * @param email The email of the user to be registered
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
