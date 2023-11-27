package requests;

/**
 * This class represents the request to log in
 */
public class LoginRequest {
    /**
     * The username of the user trying to log in
     */
    private String username;
    /**
     * The password of the user trying to log in
     */
    private String password;

    /**
     * Constructs a LoginRequest object
     * @param username The username of the user trying to log in
     * @param password The password of the user trying to log in
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the user trying to log in
     * @return The username of the user trying to log in
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user trying to log in
     * @param username The username of the user trying to log in
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user trying to log in
     * @return The password of the user trying to log in
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user trying to log in
     * @param password The password of the user trying to log in
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
