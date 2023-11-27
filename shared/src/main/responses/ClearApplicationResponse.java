package responses;

/**
 * The result of the trying to clear the database.
 */
public class ClearApplicationResponse {
    /**
     * The messages that appears when the request goes through
     */
    private String message;

    /**
     * Constructs a ClearApplicationResult object
     * @param message The message that appears when the request through
     */
    public ClearApplicationResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the message that appears when the request goes through
     * @return The message that appears when the request through
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message that appears when the request through
     * @param message The message that appears when the request through
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
