package services.responses;

/**
 * This class represents the response from attempting to log out
 */
public class LogoutResponse {
    /**
     * The message that appears from attempting to log out
     */
    private String message;

    /**
     * Constructs a LogOutResponse that represents a failed logout attempt
     * @param message The message that appears from attempting to log out
     */
    public LogoutResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the message that appears from attempting to log out
     * @return The message that appears from attempting to log out
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message that appears from attempting to log out
     * @param message The message that appears from attempting to log out
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
